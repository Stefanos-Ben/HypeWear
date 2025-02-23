package com.stephben.hypewear.apparel.presentation.apparel_list

sealed class ApparelListAction {
    data object GetApparels : ApparelListAction()

    data class OnSearchQueryChange(val query: String): ApparelListAction()
}