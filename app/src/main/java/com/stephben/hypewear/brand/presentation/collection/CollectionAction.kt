package com.stephben.hypewear.brand.presentation.collection

sealed interface CollectionAction {
    data object GetApparels: CollectionAction

    data class OnDelete(val id: String): CollectionAction
}