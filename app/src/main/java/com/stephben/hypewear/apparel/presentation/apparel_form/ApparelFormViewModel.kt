package com.stephben.hypewear.apparel.presentation.apparel_form

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.apparel.domain.ApparelFormValidateUseCase
import com.stephben.hypewear.apparel.domain.ApparelRepository
import com.stephben.hypewear.apparel.domain.BrandInfo
import com.stephben.hypewear.apparel.domain.EcoMetrics
import com.stephben.hypewear.brand.domain.Brand
import com.stephben.hypewear.brand.domain.BrandRepository
import com.stephben.hypewear.core.domain.utils.FabricLibrary
import com.stephben.hypewear.core.domain.utils.PackagingMaterials
import com.stephben.hypewear.core.domain.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.ln
import kotlin.math.roundToInt
import kotlin.math.sqrt

class ApparelFormViewModel(
    private val validate: ApparelFormValidateUseCase,
    private val repository: ApparelRepository,
    private val brandRepository: BrandRepository
) : ViewModel() {
    private val tag = "APPAREL FORM VM"
    private val _state: MutableStateFlow<ApparelFormState> = MutableStateFlow(ApparelFormState())
    val state: StateFlow<ApparelFormState> = _state.asStateFlow()

    private var sizeId = 0

    fun onAction(action: ApparelFormAction) = when (action) {
        is ApparelFormAction.OnChipToggled ->
            _state.update { it.toggleChip(action.id, action.value) }

        is ApparelFormAction.OnFieldChanged ->
            _state.update {
                it.updateField(action.id, action.value)
                    .copy(fieldErrors = it.fieldErrors - action.id)
                    .recalculateEcoScore()
            }

        is ApparelFormAction.OnAddSizeRow ->
            _state.update {
                val newKey = "new${sizeId++}"
                it.copy(stockPerSize = it.stockPerSize + (newKey to ""))
            }

        is ApparelFormAction.OnRemoveSize ->
            _state.update { it.copy(stockPerSize = it.stockPerSize - action.size) }

        ApparelFormAction.OnBackClicked -> _state.update { it.copy(step = it.step.previous()) }
        ApparelFormAction.OnNextClicked -> tryAdvance()
        ApparelFormAction.OnSubmit -> commitForm()
    }


    private fun tryAdvance() {
        val state = _state.value
        val errors = validate(state.step, state)
        if (errors.isEmpty()) _state.update { it.copy(step = it.step.next()) }
        else _state.update { it.copy(fieldErrors = errors) }
    }

    private fun commitForm() {
        viewModelScope.launch {
            val currentBrand = getCurrentBrand() ?: Brand()
            _state.update { it.copy(isLoading = true) }
            when (val result = repository.createApparel(_state.value.toDomain(currentBrand))) {
                is Result.Success -> {
                    Log.i(tag, "Apparel saved")
                    _state.update {
                        it.copy(
                            isLoading = false,
                            completed = true,
                            step = it.step.next()
                        )
                    }
                }

                is Result.Failure -> {
                    Log.e(tag, "Save failed: ${result.exception.message}")
                    _state.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    private suspend fun getCurrentBrand(): Brand? =
        when (val result = brandRepository.getCurrentBrand()) {
            is Result.Success -> result.data
            is Result.Failure -> null
        }


}


private fun ApparelFormState.updateField(id: String, value: String) = when {
    id == "description" -> copy(description = value)
    id == "imageUrl" -> copy(imageUrl = value)
    id == "color" -> copy(color = value)
    id == "sex" -> copy(sex = value)
    id == "category" -> copy(category = value)
    id == "price" -> copy(price = value)
    id == "fabricKey" -> {
        val autoMsi = FabricLibrary.items[value]?.msi?.toString() ?: higgMSI
        copy(fabricKey = value, customFabric = "", higgMSI = autoMsi)
    }
    id == "customFabric" -> copy(customFabric = value)
    id == "apparelWeight" -> copy(apparelWeight = value)
    id == "tag" -> copy(tags = tags.plus(value))
    id.startsWith("stock:") -> {
        val key = id.removePrefix("stock:")
        copy(stockPerSize = stockPerSize + (key to value))
    }
    id.startsWith("sizeKey:") -> {
        val oldKey = id.removePrefix("sizeKey:")
        val oldQty = stockPerSize[oldKey] ?: ""
        val sanitizedValue = value.ifBlank { oldKey }
        copy(
            stockPerSize = stockPerSize - oldKey + (sanitizedValue to oldQty)
        )
    }
    id == "higgMsi" -> copy(higgMSI = value)
    id == "carbonFootprint" -> copy(carbonFootprint = value)
    id == "waterFootprint" -> copy(waterFootprint = value)
    id == "packagingWeight"  -> copy(packagingWeight = value)
    id == "packagingMaterial" -> copy(packagingMaterial = value)
    id == "ecoBadges" -> copy(ecoBadges = ecoBadges.toggle(value))
    else -> this
}.recalculateEcoScore()

private fun ApparelFormState.effectiveMsi(): Double =
    higgMSI.toDoubleOrNull() ?: FabricLibrary.items[fabricKey]?.msi?.toDouble() ?: 60.0

private fun ApparelFormState.recalculateEcoScore(): ApparelFormState {
    val msi = effectiveMsi()
    val kgCO2 = carbonFootprint.toDoubleOrNull() ?: 40.0
    val liters = waterFootprint.toDoubleOrNull() ?: 5000_0.0
    val gApparel = apparelWeight.toDoubleOrNull() ?: 0.0
    val gPack = packagingWeight.toDoubleOrNull() ?: 0.0
    val pFac = PackagingMaterials.factors[packagingMaterial] ?: 1.0

    val pkgImpactPerKg = if (gApparel > 0) (gPack * pFac) / gApparel * 1_000 else 500.0


    // Normalizations
    val matScore = (1 - ((msi - 5) / 55.0)).coerceIn(0.0, 1.0) * 100
    val co2Score = (1 - (kgCO2 / 40.0)).coerceIn(0.0, 1.0) * 100
    val waterScore = (1 - (liters / 5_000)).coerceIn(0.0, 1.0) * 100
    val pkgScore = (1 - (pkgImpactPerKg / 500.0)).coerceIn(0.0, 1.0) * 100

    val weighted = 0.25 * matScore + 0.3 * co2Score + 0.25 * waterScore + 0.2 * pkgScore
    val scoreInt = weighted.roundToInt().coerceIn(0, 100)

    return copy(
        ecoScore = scoreInt,
        ecoBadges = deriveBadges(matScore, co2Score, waterScore, pkgScore)
    )
}

private fun deriveBadges(mat: Double, co2: Double, water: Double, pkg: Double) = buildSet {
    if (co2 >= 80) add("Low-Carbon")
    if (water >= 80) add("Water-Smart")
    if (mat >= 85) add("Low-Impact Materials")
    if (pkg >= 90) add("Plastic-Free Pack")
}

private fun ApparelFormState.toDomain(brand: Brand): Apparel {

    fun packagingImpactPerKg(): Double {
        val gItem  = apparelWeight.toDoubleOrNull() ?: return 0.0
        val gPack  = packagingWeight.toDoubleOrNull() ?: return 0.0
        val factor = PackagingMaterials.factors[packagingMaterial] ?: 1.0
        return if (gItem > 0) (gPack * factor) / gItem * 1_000 else 0.0
    }

    val fabricLabel = if (fabricKey == "Other") customFabric.ifBlank { "Other" }
        else FabricLibrary.items[fabricKey]?.label

    return Apparel(
        apparelID = "",     // Firestore generates it
        brand = BrandInfo(
            id = brand.id,
            name = brand.name,
            logoUrl = brand.logoUrl
        ),
        description = description,
        imageUrl = imageUrl,
        color = color,
        sex = sex,
        category = category,
        tags = tags.toList(),
        price = price.toDoubleOrNull() ?: 0.0,
        stockPerSize = stockPerSize.mapValues { (_, v) -> v.toIntOrNull() ?: 0 },
        fabric = fabricLabel ?: "Unknown fabric",
        ecoMetrics = EcoMetrics(
            materialSustainability = effectiveMsi(),
            carbonFootprint = carbonFootprint.toDoubleOrNull() ?: 0.0,
            waterFootprint = waterFootprint.toDoubleOrNull() ?: 0.0,
            packagingSustainability = packagingImpactPerKg()
        ),
        ecoScore = ecoScore,
        ecoBadges = ecoBadges.toList(),
        createdAt = "",
        updatedAt = "",
    )
}


private fun ApparelFormState.toggleChip(id: String, value: String) = when (id) {
    "tags" -> copy(tags = tags.toggle(value))
    "ecoBadges" -> copy(ecoBadges = ecoBadges.toggle(value))
    else -> this
}

private fun <T> Set<T>.toggle(value: T) = if (contains(value)) minus(value) else plus(value)


private fun FormStep.next() = FormStep.entries.getOrNull(ordinal + 1) ?: this

private fun FormStep.previous() = FormStep.entries.getOrNull(ordinal - 1) ?: this