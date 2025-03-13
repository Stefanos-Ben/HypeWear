package com.stephben.hypewear.apparel.presentation.apparel_home

sealed interface ApparelListAction {

    data object GetApparels : ApparelListAction

    data class OnSearchQueryChange(val query: String): ApparelListAction
}