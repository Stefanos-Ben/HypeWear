package com.stephben.hypewear.apparel.presentation.apparel_list

sealed class ApparelListAction {
    data object GetApparels : ApparelListAction()
    //data class AddApparel(): ApparelListActions

    data class onSearchQueryChange(val query: String): ApparelListAction()
}