package com.stephben.hypewear.apparel.presentation.apparel_form

import android.net.Uri

sealed interface ApparelFormAction{
    data class OnFieldChanged(val id: String,  val value: String): ApparelFormAction

    data class OnChipToggled(val id: String, val value: String): ApparelFormAction

    data object OnAddSizeRow: ApparelFormAction

    data class OnRemoveSize(val size: String): ApparelFormAction

    data object OnNextClicked: ApparelFormAction

    data object OnBackClicked: ApparelFormAction

    data class JumpToStep(val step: FormStep): ApparelFormAction

    data class OnImagePicked(val uri: Uri): ApparelFormAction

    data object OnSubmit: ApparelFormAction
}