package com.stephben.hypewear.apparel.presentation.apparel_detail



sealed interface ApparelDetailAction {

    data class OnSelectedApparelChange(val apparelId: String): ApparelDetailAction

    data object OnToggleFavorites: ApparelDetailAction

    data object OnCheckIsFavorite: ApparelDetailAction
}