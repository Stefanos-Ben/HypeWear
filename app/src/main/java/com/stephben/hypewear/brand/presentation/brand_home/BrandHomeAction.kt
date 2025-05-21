package com.stephben.hypewear.brand.presentation.brand_home

sealed interface BrandHomeAction {
    data object OnLoadBrand: BrandHomeAction
}