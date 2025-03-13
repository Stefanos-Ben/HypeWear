package com.stephben.hypewear.apparel.presentation.apparel_detail

import com.stephben.hypewear.apparel.domain.Apparel

data class ApparelDetailState(
    val isLoading: Boolean = false,
    val isFavorite: Boolean = false,
    val errorMessage: String? = null,
    val apparel: Apparel? = null
)