package com.stephben.hypewear.apparel.domain

import com.stephben.hypewear.core.domain.utils.Result


interface ApparelRepository {

    suspend fun createApparel(
        title: String,
        description: String,
        imageUrl: String,
        price: Double,
        currency: String = "€"
    ): Result<Unit>

    suspend fun deleteApparel(apparelId: String): Result<Unit>

    suspend fun  updateApparel(
        apparelId: String,
        title: String,
        description: String,
        imageUrl: String,
        price: Double,
        currency: String = "€"
    ): Result<Unit>

    suspend fun getAllApparels(): Result<List<Apparel>>
}