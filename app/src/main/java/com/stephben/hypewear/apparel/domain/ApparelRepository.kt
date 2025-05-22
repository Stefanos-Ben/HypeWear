package com.stephben.hypewear.apparel.domain

import com.stephben.hypewear.core.domain.utils.Result


interface ApparelRepository {

    suspend fun createApparel(apparel: Apparel): Result<Unit>

    suspend fun deleteApparel(apparelId: String): Result<Unit>

    suspend fun  updateApparel(apparel: Apparel): Result<Unit>

    suspend fun getAllApparels(): Result<List<Apparel>>

    suspend fun getNewApparels(): Result<List<Apparel>>

    suspend fun getApparel(apparelId: String): Result<Apparel>

    suspend fun getFavoriteApparels(apparelIDs: List<String>): Result<List<Apparel>>

    suspend fun searchApparels(searchQuery: String): Result<List<Apparel>>

    suspend fun getBrandApparels(brandId: String): Result<List<Apparel>>
}