package com.stephben.hypewear.apparel.data

import android.util.Log
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.stephben.hypewear.apparel.data.dtos.ApparelDto
import com.stephben.hypewear.apparel.data.mappers.toApparel
import com.stephben.hypewear.apparel.data.mappers.toDto
import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.apparel.domain.ApparelRepository
import com.stephben.hypewear.core.domain.utils.COLLECTION_APPARELS
import com.stephben.hypewear.core.domain.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull


class ApparelRepositoryImpl(
    private val hypeWearDb: FirebaseFirestore,
    private val ioDispatcher: CoroutineDispatcher
) : ApparelRepository {

    override suspend fun createApparel(apparel: Apparel): Result<Unit> {
        return try {
            withContext(ioDispatcher) {

                val trigrams = generateTrigrams(apparel.description)

                val apparelDto = apparel.toDto(trigrams = trigrams)

                val addTaskTimeout = withTimeoutOrNull(10000L) {
                    hypeWearDb
                        .collection(COLLECTION_APPARELS)
                        .add(apparelDto)
                        .await()
                }

                if (addTaskTimeout == null) {
                    return@withContext Result.Failure(
                        IllegalStateException("Please check your internet connection!")
                    )
                }

                Result.Success(Unit)
            }
        } catch (e: Exception) {
            Log.d("ADD ERROR", e.toString())
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
                        .documents.mapNotNull { it.toObject(ApparelDto::class.java)?.toApparel() }
                }

                if (fetchingApparelsTimeout == null) {
                    return@withContext Result.Failure(
                        IllegalStateException("Please check your internet connection!")
                    )
                }
                Result.Success(data = fetchingApparelsTimeout)

            }
        } catch (e: Exception) {
            Log.d("FETCHING ERROR", "ERROR: ", e)
            Result.Failure(e)
        }
    }

    override suspend fun getNewApparels(): Result<List<Apparel>> {
        return try {
            withContext(ioDispatcher) {
                val getNewApparelsTimeout = withTimeoutOrNull(10000L) {
                    hypeWearDb.collection(COLLECTION_APPARELS)
                        .orderBy("createdAt", Query.Direction.DESCENDING)
                        .limit(50)
                        .get()
                        .await()
                        .documents.mapNotNull { it.toObject(ApparelDto::class.java)?.toApparel() }
                }

                if (getNewApparelsTimeout == null) {
                    return@withContext Result.Failure(
                        IllegalStateException("Please check your internet connection!")
                    )
                }
                Result.Success(data = getNewApparelsTimeout)
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun getApparel(apparelId: String): Result<Apparel> {
        return try {
            withContext(ioDispatcher) {
                val getApparelTimeout = withTimeoutOrNull(10000L) {
                    hypeWearDb.collection(COLLECTION_APPARELS)
                        .document(apparelId)
                        .get()
                        .await()
                        .toObject(ApparelDto::class.java)?.toApparel()
                }

                if (getApparelTimeout == null) {
                    Result.Failure(
                        IllegalStateException("Please check your internet connection!")
                    )
                }
                Result.Success(data = getApparelTimeout ?: Apparel())
            }
        } catch (e: Exception) {
            Log.d("FETCHING ERROR", "ERROR: ", e)
            Result.Failure(e)
        }
    }

    override suspend fun getFavoriteApparels(apparelIDs: List<String>): Result<List<Apparel>> {
        return try {
            withContext(ioDispatcher) {
                val results = mutableListOf<Apparel>()
                val chunkedIDs = apparelIDs.chunked(10) // Firestore whereIn() supports max 10 items
                for (chunk in chunkedIDs) {
                    val apparels = withTimeoutOrNull(10000L) {
                        hypeWearDb.collection(COLLECTION_APPARELS)
                            .whereIn(FieldPath.documentId(), chunk)
                            .get()
                            .await()
                            .documents.mapNotNull {
                                it.toObject(ApparelDto::class.java)?.toApparel()
                            }
                    }
                    if (apparels == null) {
                        Log.e("APPAREL REPOSITORY", "Check your internet connection")
                        return@withContext Result.Failure(
                            IllegalStateException("Please check your internet connection!")
                        )
                    }
                    results.addAll(apparels)
                }
                Result.Success(data = results)
            }
        } catch (e: Exception) {
            Log.e("APPAREL REPOSITORY", "ERROR: ${e.message}")
            Result.Failure(e)
        }
    }

    override suspend fun searchApparels(searchQuery: String): Result<List<Apparel>> {
        return try {
            withContext(ioDispatcher) {
                // If the search query is unable to produce trigrams search with starts ends with.
                val searchApparelsTimeout: List<Apparel>? = if (
                    searchQuery.isBlank() || searchQuery.trim().length < 3
                ) {
                    withTimeoutOrNull(10000L) {
                        hypeWearDb
                            .collection(COLLECTION_APPARELS)
                            .orderBy("title")
                            .startAt(searchQuery)
                            .endAt(searchQuery + "\uf8ff")
                            .get()
                            .await()
                            .documents
                            .mapNotNull { it.toObject(ApparelDto::class.java)?.toApparel() }
                    }
                } else {
                    val searchTrigrams = generateTrigrams(searchQuery)

                    // Firebase query limit is 30 for arrays.
                    val limitedTrigrams =
                        if (searchTrigrams.size > 30) searchTrigrams.take(30) else searchTrigrams
                    Log.d("TRIGRAMS", "The limited trigrams are $limitedTrigrams")

                    withTimeoutOrNull(10000L) {
                        hypeWearDb
                            .collection(COLLECTION_APPARELS)
                            .whereArrayContainsAny("trigrams", limitedTrigrams)
                            .orderBy("title")
                            .get()
                            .await()
                            .documents
                            .mapNotNull { it.toObject(ApparelDto::class.java)?.toApparel() }
                    }
                }

                if (searchApparelsTimeout == null) {
                    return@withContext Result.Failure(
                        IllegalStateException("Please check your internet connection!")
                    )
                }
                Result.Success(searchApparelsTimeout)
            }
        } catch (e: Exception) {
            Log.d("SEARCHING ERROR", "ERROR: ", e)
            Result.Failure(e)
        }
    }

    override suspend fun getBrandApparels(brandId: String): Result<List<Apparel>> {
        return try {
            withContext(ioDispatcher) {
                val getBrandApparelsTimeout = withTimeoutOrNull(10000L) {
                    hypeWearDb.collection(COLLECTION_APPARELS)
                        .whereEqualTo("brand.id", brandId)
                        .orderBy("createdAt")
                        .get()
                        .await()
                        .documents.mapNotNull { it.toObject(ApparelDto::class.java)?.toApparel() }
                }

                if (getBrandApparelsTimeout == null) {
                    return@withContext Result.Failure(
                        IllegalStateException("Please check your internet connection!")
                    )
                }
                Result.Success(data = getBrandApparelsTimeout)
            }
        } catch (e: Exception) {
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
                    return@withContext Result.Failure(
                        IllegalStateException("Please check your internet connection!")
                    )
                }

                Result.Success(Unit)
            }

        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun updateApparel(apparel: Apparel): Result<Unit> {
        return try {

            withContext(ioDispatcher) {

                val trigrams = generateTrigrams(apparel.description)

                val apparelDto = apparel.toDto(
                    trigrams = trigrams
                )
                val updateTaskTimeout = withTimeoutOrNull(10000L) {
                    hypeWearDb
                        .collection(COLLECTION_APPARELS)
                        .document(apparel.apparelID)
                        .set(apparelDto, SetOptions.merge())
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