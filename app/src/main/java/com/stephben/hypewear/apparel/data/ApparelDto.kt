package com.stephben.hypewear.apparel.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class ApparelDto(
    @DocumentId
    val id: String,

    val title: String = "",



)
