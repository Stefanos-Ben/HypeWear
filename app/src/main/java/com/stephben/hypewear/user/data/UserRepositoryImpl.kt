package com.stephben.hypewear.user.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.stephben.hypewear.core.domain.utils.Result
import com.stephben.hypewear.core.domain.utils.USERS_COLLECTION

import com.stephben.hypewear.user.domain.User
import com.stephben.hypewear.user.domain.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.suspendCoroutine

class UserRepositoryImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val ioDispatcher: CoroutineDispatcher
): UserRepository {

    private val tag = "User repository"

    override suspend fun signUpWithEmail(
        email: String,
        password: String,
        displayName: String
    ): Result<Unit> {
        return try {
//            val userCredential = auth.createUserWithEmailAndPassword(email, password).await()
//            val firebaseUser = userCredential.user
//                ?: return Result.Failure(IllegalStateException("Auth user is null after sign-up."))
//
//            // Send email verification
//            firebaseUser.sendEmailVerification().await()
//
//            val userDoc = User(
//                userId = firebaseUser.uid,
//                email = email,
//                displayName = displayName,
//                isEmailVerified = firebaseUser.isEmailVerified,
//                createdAt = System.currentTimeMillis(),
//                updatedAt = System.currentTimeMillis()
//            )
//
//            firestore.collection(USERS_COLLECTION)
//                .document(firebaseUser.uid)
//                .set(userDoc)
//                .await()
//
//            val profileUpdate = UserProfileChangeRequest.Builder()
//                .setDisplayName(displayName)
//                .build()
//            firebaseUser.updateProfile(profileUpdate).await()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { firebaseUser ->

                    CoroutineScope(ioDispatcher).launch {
                        val userDoc = User(
                            userId = firebaseUser.user!!.uid,
                            email = email,
                            displayName = displayName,
                            isEmailVerified = firebaseUser.user!!.isEmailVerified,
                            createdAt = System.currentTimeMillis(),
                            updatedAt = System.currentTimeMillis()
                        )

                        firestore.collection(USERS_COLLECTION)
                            .document(firebaseUser.user!!.uid)
                            .set(userDoc)
                            .await()

                        signInWithEmail(email, password)
                    }

                    println(tag + "Register Success")
                }
                .addOnFailureListener {
                    println(tag + "Register Failure")
                }
            Result.Success(Unit)
        } catch (e: FirebaseAuthUserCollisionException) {
            println(tag + "login exception ${e.message}")
            Result.Failure(e)
        } catch (e: Exception) {
            println(tag + "login exception ${e.message}")
            Result.Failure(e)
        }
    }

    override suspend fun signInWithEmail(
        email: String,
        password: String,
    ): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.Success(Unit)
        } catch (e: Exception){
            println(tag + "login exception ${e.message}")
            Result.Failure(e)
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    override suspend fun getCurrentUserDoc(): Result<User> {
        val currentUser = auth.currentUser
            ?: return Result.Failure(IllegalStateException("No user is logged in."))
        return getUserById(currentUser.uid)
    }

    override suspend fun getUserById(userId: String): Result<User> {
        return try {

            val snapshot = firestore.collection(USERS_COLLECTION)
                .document(userId)
                .get()
                .await()


            val userObj = snapshot.toObject(User::class.java)
                ?: return Result.Failure(NoSuchElementException("User not found in Firestore."))

            Result.Success(data = userObj)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun signInWithGoogle(idToken: String): Result<Unit> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential).await()

            val firebaseUser = auth.currentUser ?: return Result.Failure(
                IllegalStateException("FirebaseUser in null after Google sign-in.")
            )

            firestore.collection(USERS_COLLECTION)
                .document(firebaseUser.uid)
                .get()
                .await()

            Result.Success(Unit)
        } catch (e:Exception) {
            Result.Failure(e)
        }
    }


}