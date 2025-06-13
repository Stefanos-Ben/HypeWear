package com.stephben.hypewear.auth.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore
import com.stephben.hypewear.auth.domain.AuthRepository
import com.stephben.hypewear.brand.data.dtos.BrandDto
import com.stephben.hypewear.brand.data.mappers.toBrand
import com.stephben.hypewear.core.domain.utils.COLLECTION_BRANDS
import com.stephben.hypewear.core.domain.utils.Result
import com.stephben.hypewear.core.domain.utils.COLLECTION_USERS
import com.stephben.hypewear.user.data.dtos.UserDto
import com.stephben.hypewear.user.data.mappers.toUser
import com.stephben.hypewear.user.domain.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

private const val tag = "AUTH REPOSITORY"

class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val ioDispatcher: CoroutineDispatcher
):AuthRepository {
    override suspend fun signUpWithEmail(
        email: String,
        password: String,
        displayName: String
    ): Result<Unit> {
        return try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { firebaseUser ->
                    CoroutineScope(ioDispatcher).launch {
                        val userDoc = UserDto(
                            userId = firebaseUser.user!!.uid,
                            email = email,
                            displayName = displayName,
                            isEmailVerified = firebaseUser.user!!.isEmailVerified,
                            userType = "default",
                            favorites = emptyList(),
                            cart = emptyList()
                        )

                        firestore.collection(COLLECTION_USERS)
                            .document(firebaseUser.user!!.uid)
                            .set(userDoc)
                            .await()

                        signInWithEmail(email, password)
                    }
                    Log.i(tag, "Signed Up successfully")
                }
                .addOnFailureListener {
                    Log.e(tag, "Sign Up Failure")
                }
            Result.Success(Unit)
        } catch (e: FirebaseAuthUserCollisionException) {
            Log.e(tag, "Sign up failed: ${ e.message }")
            Result.Failure(e)
        } catch (e: Exception) {
            Log.e(tag, "Sign up failed: ${ e.message }")
            Result.Failure(e)
        }
    }

    override suspend fun signUpBrandWithEmail(
        email: String,
        password: String,
        displayName: String
    ): Result<Unit> {
        return try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { firebaseUser ->
                    CoroutineScope(ioDispatcher).launch {

                        val brandDoc = BrandDto(
                            name = displayName,
                            userId = firebaseUser.user!!.uid,
                            contactEmail = email,
                        )

                        val document = firestore
                            .collection(COLLECTION_BRANDS)
                            .add(brandDoc)
                            .await()
                            .get()
                            .await()
                            .toObject(BrandDto::class.java)!!.toBrand()

                        val userDoc = UserDto(
                            userId = firebaseUser.user!!.uid,
                            email = email,
                            displayName = displayName,
                            userType = "brand",
                            isEmailVerified = firebaseUser.user!!.isEmailVerified,
                            brandId = document.id
                        )

                        firestore.collection(COLLECTION_USERS)
                            .document(firebaseUser.user!!.uid)
                            .set(userDoc)
                            .await()


                        signInWithEmail(email, password)
                    }
                    Log.i(tag, "Brand registered successfully!")
                }
                .addOnFailureListener {
                    Log.e(tag, "Brand sign up Failure")
                }
            Result.Success(Unit)
        } catch (e: FirebaseAuthUserCollisionException) {
            Log.e(tag, "Brand sign up failed: ${ e.message }")
            Result.Failure(e)
        } catch (e: Exception) {
            Log.e(tag, "Brand sign up failed: ${ e.message }")
            Result.Failure(e)
        }
    }

    override suspend fun signInWithEmail(email: String, password: String): Result<User> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            val uid = auth.currentUser!!.uid
            val user = firestore.collection(COLLECTION_USERS)
                .document(uid)
                .get()
                .await()
                .toObject(UserDto::class.java)?.toUser() ?: error("User doc missing")

            Result.Success(user)
        } catch (e: Exception) {
            Log.e(tag, "Sign in failed: ${e.message}")
            Result.Failure(e)
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override fun isUserLoggedIn(): Boolean = auth.currentUser != null

    override fun currentUserId(): String? = auth.currentUser?.uid


    override suspend fun updateVerificationStatus(id: String): Result<Unit> {
        return try {
            firestore
                .collection(COLLECTION_USERS)
                .document(id)
                .update("emailVerified", true)
                .await()
            Log.e("AUTH REPOSITORY", "Updated EmailVerification status")
            Result.Success(Unit)
        } catch (e: Exception){
            Log.e("AUTH REPOSITORY", "Couldn't update EmailVerification status")
            Result.Failure(e)
        }

    }

    override suspend fun signInWithGoogle(idToken: String): Result<Unit> {
        TODO("Not yet implemented")
    }

}