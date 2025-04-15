package com.stephben.hypewear.brand.data

import com.google.firebase.firestore.FirebaseFirestore
import com.stephben.hypewear.brand.data.dtos.BrandDto
import com.stephben.hypewear.brand.data.mappers.toBrand
import com.stephben.hypewear.brand.domain.Brand
import com.stephben.hypewear.brand.domain.BrandRepository
import com.stephben.hypewear.core.domain.utils.COLLECTION_BRANDS
import kotlinx.coroutines.CoroutineDispatcher
import com.stephben.hypewear.core.domain.utils.Result
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull

class BrandRepositoryImpl (
    private val hypeWearDb: FirebaseFirestore,
    private val ioDispatcher: CoroutineDispatcher
): BrandRepository {
    override suspend fun getAllBrands(): Result<List<Brand>> {
        return try {
                withContext(ioDispatcher) {
                    val fetchingBrandsTimeout = withTimeoutOrNull(10000L) {
                        hypeWearDb.collection(COLLECTION_BRANDS)
                            .get()
                            .await()
                            .documents.mapNotNull { it.toObject(BrandDto::class.java)?.toBrand() }
                    }

                    if (fetchingBrandsTimeout == null) {
                        return@withContext Result.Failure(
                            IllegalStateException("Please check your internet connection!")
                        )
                    }
                    Result.Success(data = fetchingBrandsTimeout)
                }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}