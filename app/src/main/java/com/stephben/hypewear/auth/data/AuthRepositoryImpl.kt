package com.stephben.hypewear.auth.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore
import com.stephben.hypewear.auth.domain.AuthRepository
import com.stephben.hypewear.core.domain.utils.Result
import com.stephben.hypewear.core.domain.utils.USERS_COLLECTION
import com.stephben.hypewear.user.domain.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

private val tag = "AUTH REPOSITORY"

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
                        val userDoc = User(
                            userId = firebaseUser.user!!.uid,
                            email = email,
                            displayName = displayName,
                            isEmailVerified = firebaseUser.user!!.isEmailVerified,
                            createdAt = System.currentTimeMillis(),
                            updatedAt = System.currentTimeMillis(),
                        )

                        firestore.collection(USERS_COLLECTION)
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

    override suspend fun signInWithEmail(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    Log.i(tag, "Logged in successfully as $email")
                }
                .addOnFailureListener {
                    Log.e(tag, "Some error occurred while Signing In, check internet connectivity")
                }
            Result.Success(Unit)
        } catch (e:Exception) {
            Log.e(tag, "Error in Sign In")
            Result.Failure(e)
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override fun isUserLoggedIn(): Boolean = auth.currentUser != null

    override fun currentUserId(): String? = auth.currentUser?.uid


    override suspend fun signInWithGoogle(idToken: String): Result<Unit> {
        TODO("Not yet implemented")
    }

}