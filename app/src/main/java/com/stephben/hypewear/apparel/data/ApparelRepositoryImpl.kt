package com.stephben.hypewear.apparel.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.apparel.domain.ApparelRepository
import com.stephben.hypewear.core.domain.utils.COLLECTION_APPARELS
import com.stephben.hypewear.core.domain.utils.Result
import com.stephben.hypewear.core.domain.utils.convertDateFormat
import com.stephben.hypewear.core.domain.utils.getCurrentTimeAsTimestamp
import com.stephben.hypewear.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

class ApparelRepositoryImpl @Inject constructor(
    private val hypeWearDb: FirebaseFirestore,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ApparelRepository {
    override suspend fun createApparel(
        title: String,
        description: String,
        imageUrl: String,
        price: Double,
        currency: String
    ): Result<Unit> {
        return try {
            withContext(ioDispatcher) {
                val apparel = hashMapOf(
                    "title" to title,
                    "description" to description,
                    "imageUrl" to imageUrl,
                    "price" to price,
                    "currency" to currency,
                    "createdAt" to getCurrentTimeAsTimestamp(),
                    "updateAt" to getCurrentTimeAsTimestamp()
                )

                val addTaskTimeout = withTimeoutOrNull(10000L) {
                    hypeWearDb.collection(COLLECTION_APPARELS)
                        .add(apparel)
                }

                if (addTaskTimeout == null) {
                    Result.Failure(
                        IllegalStateException("Please check your internet connection!")
                    )
                }
                Result.Success(Unit)
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun getAllApparels(): Result<List<Apparel>> {
        return try {
            withContext(ioDispatcher) {
                val fetchingApparelsTimeout = withTimeoutOrNull(10000L) {
                    hypeWearDb.collection("apparels")
                        .get()
                        .await()
                        .documents.map { document ->
                            Apparel(
                                apparelID = document.id,
                                title = document.getString("title") ?: "",
                                description = document.getString("description") ?: "",
                                imageUrl = document.getString("imageUrl") ?: "",
                                price = document.getDouble("price") ?: 0.0,
                                currency = document.getString("currency") ?: "€",
                                createdAt = convertDateFormat(
                                    date = document.getDate("createdAt")
                                ),
                                updatedAt = convertDateFormat(
                                    date = document.getDate("updatedAt")
                                )
                            )
                        }
                }

                if (fetchingApparelsTimeout == null) {
                    Result.Failure(

                        IllegalStateException("Please check your internet connection!")
                    )
                }
                Result.Success(fetchingApparelsTimeout?.toList() ?: emptyList())

            }
        } catch (e: Exception) {
            Log.d("FETCHING ERROR", "ERROR: ", e)
            Result.Failure(e)
        }
    }

    override suspend fun deleteApparel(apparelId: String): Result<Unit> {
        return try {
            withContext(ioDispatcher) {
                val deleteApparelTimeout = withTimeoutOrNull(10000L) {
                    hypeWearDb
                        .collection(COLLECTION_APPARELS)
                        .document(apparelId)
                        .delete()
                }

                if (deleteApparelTimeout == null) {
                    Result.Failure(
                        IllegalStateException("Please check your internet connection!")
                    )
                }

                Result.Success(Unit)
            }

        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun updateApparel(
        apparelId: String,
        title: String,
        description: String,
        imageUrl: String,
        price: Double,
        currency: String
    ): Result<Unit> {
        return try {
            withContext(ioDispatcher) {
                val updatedApparel = mapOf(
                    "title" to title,
                    "description" to description,
                    "imageUrl" to imageUrl,
                    "price" to price,
                    "currency" to currency,
                    "updateAt" to getCurrentTimeAsTimestamp()
                )
                val updateTaskTimeout = withTimeoutOrNull(10000L) {
                    hypeWearDb
                        .collection(COLLECTION_APPARELS)
                        .document(apparelId)
                        .update(updatedApparel)
                }

                if (updateTaskTimeout == null) {
                    Result.Failure(
                        IllegalStateException("Please check your internet connection!")
                    )
                }
                Result.Success(Unit)
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

}