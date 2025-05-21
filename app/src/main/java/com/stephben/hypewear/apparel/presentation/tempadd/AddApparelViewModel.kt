package com.stephben.hypewear.apparel.presentation.tempadd

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.apparel.domain.ApparelRepository
import com.stephben.hypewear.apparel.domain.BrandInfo
import com.stephben.hypewear.apparel.domain.EcoMetrics
import com.stephben.hypewear.brand.domain.BrandRepository
import com.stephben.hypewear.core.domain.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class AddApparelViewModel(
    private val apparelRepository: ApparelRepository,
    private val brandRepository: BrandRepository
) : ViewModel() {

    private val _state: MutableStateFlow<AddApparelState> = MutableStateFlow(AddApparelState())
    val state: StateFlow<AddApparelState> = _state.asStateFlow()

    fun onAction(action: AddApparelAction) {
        reduce(action = action)
    }

    private fun reduce(action: AddApparelAction) {
        when (action) {
            is AddApparelAction.FetchBrands -> fetchBrands()

            is AddApparelAction.OnBrandsLoaded -> {
                _state.update { it.copy(brands = action.brands) }
            }

            is AddApparelAction.OnSelectBrand -> {
                _state.update { it.copy(selectedBrand = action.brand) }
            }

            // Eco & Apparel fields
            is AddApparelAction.OnDescriptionChange ->
                _state.update { it.copy(description = action.description) }
            is AddApparelAction.OnImageUrlChange ->
                _state.update { it.copy(imageUrl = action.imageUrl) }
            is AddApparelAction.OnPriceChange ->
                _state.update { it.copy(price = action.price) }
            is AddApparelAction.OnDiscountChange ->
                _state.update { it.copy(discount = action.discount) }
            is AddApparelAction.OnCurrencyChange ->
                _state.update { it.copy(currency = action.currency) }
            is AddApparelAction.OnColorChange ->
                _state.update { it.copy(color = action.color) }
            is AddApparelAction.OnFabricChange ->
                _state.update { it.copy(fabric = action.fabric) }

            is AddApparelAction.OnMaterialSustainabilityChange ->
                _state.update { it.copy(materialSustainability = action.value) }
            is AddApparelAction.OnCarbonFootprintChange ->
                _state.update { it.copy(carbonFootprint = action.value) }
            is AddApparelAction.OnWaterFootprintChange ->
                _state.update { it.copy(waterFootprint = action.value) }
            is AddApparelAction.OnPackagingSustainabilityChange ->
                _state.update { it.copy(packagingSustainability = action.value) }
            is AddApparelAction.OnEcoScoreChange ->
                _state.update { it.copy(ecoScore = action.value) }
            is AddApparelAction.OnEcoBadgesChange ->
                _state.update { it.copy(ecoBadges = action.badges) }

            is AddApparelAction.OnAddSubmit -> addApparel()
        }
    }

    private fun fetchBrands() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val result = brandRepository.getAllBrands()) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            brands = result.data
                        )
                    }
                }
                is Result.Failure -> {
                    _state.update { it.copy(isLoading = false) }
                    // handle error, e.g., show a toast in the UI
                }
            }
        }
    }


    private fun addApparel() {
        val current = _state.value
        val selectedBrand = current.selectedBrand ?: return

        val apparel = Apparel(
            // We'll leave apparelID blank; Firestore will generate an ID

            // brand fields
            brand = BrandInfo(
                id = selectedBrand.id,
                name = selectedBrand.name,
                logoUrl = selectedBrand.logoUrl
            ),

            // main info
            description = current.description,
            imageUrl = current.imageUrl,
            price = current.price.toDoubleOrNull() ?: 0.0,
            discount = current.discount.toDoubleOrNull() ?: 0.0,
            currency = current.currency,

            color = current.color,
            fabric = current.fabric,

            // eco fields
            ecoMetrics = EcoMetrics(
                materialSustainability = current.materialSustainability.toDoubleOrNull() ?: 0.0,
                carbonFootprint = current.carbonFootprint.toDoubleOrNull() ?: 0.0,
                waterFootprint = current.waterFootprint.toDoubleOrNull() ?: 0.0,
                packagingSustainability = current.packagingSustainability.toDoubleOrNull() ?: 0.0
            ),
            ecoScore = current.ecoScore.toIntOrNull() ?: 0,
            ecoBadges = current.ecoBadges
                .split(",")
                .map { it.trim() }
                .filter { it.isNotBlank() },

            // We can keep sex, category, tags, etc. as default for now
        )

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            when (val result = apparelRepository.createApparel(apparel)) {
                is Result.Success -> {
                    // Reset state fields after success
                    _state.update {
                        it.copy(
                            isLoading = false,
                            selectedBrand = null,
                            description = "",
                            imageUrl = "",
                            price = "0.0",
                            discount = "0.0",
                            currency = "€",

                            color = "",
                            fabric = "",

                            materialSustainability = "0.0",
                            carbonFootprint = "0",
                            waterFootprint = "0",
                            packagingSustainability = "0.0",
                            ecoScore = "0",
                            ecoBadges = ""
                        )
                    }
                }
                is Result.Failure -> {
                    _state.update { it.copy(isLoading = false) }
                    Log.e("ApparelADD", result.exception.message.toString())
                }
            }
        }
    }
}