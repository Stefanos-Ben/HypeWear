package com.stephben.hypewear.apparel.presentation.apparel_detail



sealed interface ApparelDetailAction {

    data class OnSelectedApparelChange(val apparelId: String): ApparelDetailAction

    data object OnToggleFavorites: ApparelDetailAction

    data object OnCheckIsFavorite: ApparelDetailAction

    data class OnSizeSelected(val size: String): ApparelDetailAction

    data object OnAddToCart: ApparelDetailAction

    data object OnQuantityAdd: ApparelDetailAction

    data object OnQuantitySubtract: ApparelDetailAction
}