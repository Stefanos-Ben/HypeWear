package com.stephben.hypewear.apparel.presentation.apparel_detail



sealed interface ApparelDetailAction {
    data class OnSelectedApparelChange(val apparelId: String): ApparelDetailAction
}