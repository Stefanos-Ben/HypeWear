package com.stephben.hypewear.apparel.presentation.apparel_detail

import com.stephben.hypewear.apparel.domain.Apparel

sealed interface ApparelDetailAction {
    data object OnBackClick: ApparelDetailAction

    data class OnSelectedApparelChange(val apparelId: String): ApparelDetailAction
}