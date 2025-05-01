package com.stephben.hypewear.user.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.stephben.hypewear.core.domain.utils.Result
import com.stephben.hypewear.core.domain.utils.USERS_COLLECTION
import com.stephben.hypewear.user.data.dtos.UserDto
import com.stephben.hypewear.user.data.mappers.toUser
import com.stephben.hypewear.user.domain.User
import com.stephben.hypewear.user.domain.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull

class UserRepositoryImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val ioDispatcher: CoroutineDispatcher
) : UserRepository {

    private val tag = "User repository"


    override suspend fun getCurrentUserDoc(): Result<User> {

        val currentUser = auth.currentUser
            ?: return Result.Failure(IllegalStateException("No user is logged in."))
        return getUserById(currentUser.uid)
        //TODO("Add network timeout clause")
    }

    override suspend fun getUserFavorites(): Result<List<String>> {
        return try {
            when (val result = getCurrentUserDoc()) {
                is Result.Success -> {
                    Log.i(tag, "Successfully fetched favorites")
                    Result.Success(data = result.data.favorites)
                }

                is Result.Failure -> {
                    Log.i(tag, "Error fetching favorites")
                    throw result.exception
                }
            }
        } catch (e: Exception) {
            Result.Failure(exception = e)
        }
    }

    override suspend fun addUserFavorites(apparelId: String): Result<Unit> {
        return try {
            val currentUser = auth.currentUser
            withContext(ioDispatcher) {
                firestore.collection(USERS_COLLECTION)
                    .document(currentUser!!.uid)
                    .update("favorites", FieldValue.arrayUnion(apparelId))
                    .await()
                Log.i(tag, "Added $apparelId to favorites")
                Result.Success(Unit)
            }
        } catch (e: Exception) {
            Log.e(tag, "Error updating favorites")
            Result.Failure(exception = e)
        }
    }

    override suspend fun removeUserFavorites(apparelId: String): Result<Unit> {
        return try {
            val currentUser = auth.currentUser
            withContext(ioDispatcher) {
                firestore.collection(USERS_COLLECTION)
                    .document(currentUser!!.uid)
                    .update("favorites", FieldValue.arrayRemove(apparelId))
                Log.i(tag, "Removed $apparelId to favorites")
                Result.Success(Unit)
            }
        } catch (e: Exception) {
            Log.e(tag, "Error updating favorites")
            Result.Failure(exception = e)
        }
    }

    override suspend fun isFavorite(apparelId: String): Boolean {
        return try {
            val currentUser = auth.currentUser
            withContext(ioDispatcher) {
                val fetchFavorites = withTimeoutOrNull(10000L) {
                    firestore.collection(USERS_COLLECTION)
                        .document(currentUser!!.uid)
                        .get()
                        .await()
                        .get("favorites") as? List<*>
                }

                if (fetchFavorites == null) {
                    Log.e(tag, "Error getting favorites")
                    return@withContext false
                }

                val isFavorite = fetchFavorites.contains(apparelId)

                Log.e("IS FAV METHOD", "The method returned $isFavorite")

                isFavorite
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getUserCart(): Result<List<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserCart(apparelId: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(userId: String): Result<User> {
        return try {
            withContext(ioDispatcher) {
                val fetchUser = withTimeoutOrNull(10000L) {
                    firestore.collection(USERS_COLLECTION)
                        .document(userId)
                        .get()
                        .await()
                        .toObject(UserDto::class.java)?.toUser()
                }

                if (fetchUser == null) {
                    Log.e(tag, "Error fetching user")
                    return@withContext Result.Failure(
                        exception = IllegalStateException("Please check your internet connection!")
                    )
                }
                Log.i(tag, "User fetched successfully")
                Result.Success(data = fetchUser)
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }


}