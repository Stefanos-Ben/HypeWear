package com.stephben.hypewear.brand.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.stephben.hypewear.brand.data.dtos.BrandDto
import com.stephben.hypewear.brand.data.mappers.toBrand
import com.stephben.hypewear.brand.domain.Brand
import com.stephben.hypewear.brand.domain.BrandRepository
import com.stephben.hypewear.core.domain.utils.COLLECTION_BRANDS
import com.stephben.hypewear.core.domain.utils.Result
import com.stephben.hypewear.core.domain.utils.USERS_COLLECTION
import com.stephben.hypewear.user.data.dtos.UserDto
import com.stephben.hypewear.user.data.mappers.toUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull

class BrandRepositoryImpl (
    private val hypeWearDb: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val ioDispatcher: CoroutineDispatcher
): BrandRepository {

    private val tag = "BRAND REPO"

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

    override suspend fun getCurrentBrand(): Result<Brand> {
        return try {
            val currentUser = auth.currentUser ?: throw IllegalStateException("No user is logged in")

            withContext(ioDispatcher){
                val userResult = withTimeoutOrNull(10000L){
                    hypeWearDb.collection(USERS_COLLECTION)
                        .document(currentUser.uid)
                        .get()
                        .await()
                        .toObject(UserDto::class.java)!!.toUser()
                } ?: throw IllegalStateException("Check your internet connection")

                val brandId = userResult.brandId

                val brandResult = withTimeoutOrNull(10000L){
                    hypeWearDb.collection(COLLECTION_BRANDS)
                        .document(brandId)
                        .get()
                        .await()
                        .toObject(BrandDto::class.java)!!.toBrand()
                } ?: throw IllegalStateException("Check your internet connection")

                Log.i(tag, "Brand fetched successfully!")
                Result.Success(brandResult)
            }
        } catch (e: Exception) {
            Log.e(tag, "Error fetching user's brand: ${e.message}")
            Result.Failure(e)
        }
    }


}