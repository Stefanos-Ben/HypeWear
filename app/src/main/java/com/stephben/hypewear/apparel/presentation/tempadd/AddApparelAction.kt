package com.stephben.hypewear.apparel.presentation.tempadd


sealed class AddApparelAction {

    data class OnTitleChange(val title: String) : AddApparelAction()

    data class OnDescriptionChange(val description: String): AddApparelAction()

    data class OnPriceChange(val price: String): AddApparelAction()

    data class OnImageUrlChange(val imageUrl: String): AddApparelAction()

    data object OnAddSubmit : AddApparelAction()
}