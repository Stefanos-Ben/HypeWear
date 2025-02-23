package com.stephben.hypewear.apparel.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.apparel.domain.ApparelRepository
import com.stephben.hypewear.core.domain.utils.COLLECTION_APPARELS
import com.stephben.hypewear.core.domain.utils.Result
import com.stephben.hypewear.core.domain.utils.convertDateFormat
import com.stephben.hypewear.core.domain.utils.getCurrentTimeAsTimestamp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull


class ApparelRepositoryImpl(
    private val hypeWearDb: FirebaseFirestore,
    private val ioDispatcher: CoroutineDispatcher
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

                val trigrams = generateTrigrams(description)

                Log.d("TRIGRAMS", trigrams.toString())

                val apparel = hashMapOf(
                    "title" to title,
                    "description" to description,
                    "imageUrl" to imageUrl,
                    "price" to price,
                    "currency" to currency,
                    "trigrams" to trigrams,
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
                    hypeWearDb.collection(COLLECTION_APPARELS)
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

    override suspend fun searchApparels(searchQuery: String): Result<List<Apparel>> {
        var searchApparelsTimeout: List<Apparel>?

        return try {
            withContext(ioDispatcher) {

                // If the search query is unable to produce trigrams search with starts ends with.
                if (searchQuery.isEmpty() || searchQuery.trim().length < 3){
                    searchApparelsTimeout = withTimeoutOrNull(10000L) {
                        hypeWearDb
                            .collection("apparels")
                            .orderBy("title")
                            .startAt(searchQuery)
                            .endAt(searchQuery+"\uf8ff")
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
                }
                else {
                    val searchTrigrams = generateTrigrams(searchQuery)

                    // Firebase query limit is 30 for arrays.
                    val limitedTrigrams = if(searchTrigrams.size > 30) searchTrigrams.take(30) else searchTrigrams
                    Log.d("TRIGRAMS", "The limited trigrams are $limitedTrigrams")


                    searchApparelsTimeout = withTimeoutOrNull(10000L) {
                        hypeWearDb
                            .collection(COLLECTION_APPARELS)
                            .whereArrayContainsAny("trigrams", limitedTrigrams)
                            .orderBy("title")
                            .get()
                            .await()
                            .documents.map { document ->
                                Log.d("SEARCH RESULT", document.data.toString())
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
                }

                if (searchApparelsTimeout == null) {
                    Result.Failure(
                        IllegalStateException("Please check your internet connection!")
                    )
                }
                Result.Success(searchApparelsTimeout?.toList() ?: emptyList())
            }
        } catch (e: Exception) {
            Log.d("SEARCHING ERROR", "ERROR: ", e)
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

    private fun generateTrigrams(input: String): List<String> {
        val trigrams = mutableListOf<String>()
        // For simplicity, make all lower case and remove any extra spaces,
        // punctuation, etc. (you can customize this to your use-case).
        val sanitized = input.lowercase().replace("\\s+".toRegex(), "")

        for (i in 0..sanitized.length - 3) {
            trigrams.add(sanitized.substring(i, i + 3))
        }

        return trigrams
    }

}