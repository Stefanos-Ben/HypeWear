package com.stephben.hypewear.brand.presentation.collection

import com.stephben.hypewear.apparel.domain.Apparel

data class CollectionState(
    val isLoading: Boolean = false,
    val apparels: List<Apparel> = emptyList(),
    val errorMessage : String? = null
)
