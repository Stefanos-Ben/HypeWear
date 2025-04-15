package com.stephben.hypewear.apparel.presentation.tempadd

import com.stephben.hypewear.brand.domain.Brand


sealed class AddApparelAction {

    data class OnDescriptionChange(val description: String) : AddApparelAction()
    data class OnImageUrlChange(val imageUrl: String) : AddApparelAction()
    data class OnPriceChange(val price: String) : AddApparelAction()
    data class OnDiscountChange(val discount: String) : AddApparelAction()
    data class OnCurrencyChange(val currency: String) : AddApparelAction()

    object FetchBrands : AddApparelAction()
    data class OnBrandsLoaded(val brands: List<Brand>) : AddApparelAction()
    data class OnSelectBrand(val brand: Brand) : AddApparelAction()

    data class OnColorChange(val color: String) : AddApparelAction()
    data class OnFabricChange(val fabric: String) : AddApparelAction()

    // Eco fields
    data class OnMaterialSustainabilityChange(val value: String) : AddApparelAction()
    data class OnCarbonFootprintChange(val value: String) : AddApparelAction()
    data class OnWaterFootprintChange(val value: String) : AddApparelAction()
    data class OnPackagingSustainabilityChange(val value: String) : AddApparelAction()
    data class OnEcoScoreChange(val value: String) : AddApparelAction()
    data class OnEcoBadgesChange(val badges: String) : AddApparelAction()

    object OnAddSubmit : AddApparelAction()
}