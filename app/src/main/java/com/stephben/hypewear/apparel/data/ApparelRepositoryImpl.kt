package com.stephben.hypewear.apparel.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.apparel.domain.ApparelRepository
import com.stephben.hypewear.core.domain.utils.Result
import kotlinx.coroutines.tasks.await

class ApparelRepositoryImpl(
    private val firestore: FirebaseFirestore
):ApparelRepository {
    override suspend fun addApparel(
        title: String,
        description: String,
        imageUrl: String,
        price: Double,
        currency: String
    ): Result<Unit> {
        return try{
            val documentRef = firestore.collection("apparel").document()
            val newApparel = Apparel(
                apparelID = documentRef.id,
                title = title,
                description = description,
                imageUrl = imageUrl,
                price = price,
                currency = currency
            )
            documentRef.set(newApparel, SetOptions.merge()).await()
            Result.Success(Unit)
        } catch (e: Exception){
            Result.Failure(e)
        }
    }

    override suspend fun getAllApparels(): Result<List<Apparel>> {
        return try {
            val snapshot = firestore.collection("apparel").get().await()
            val items = snapshot.toObjects(Apparel::class.java)
            Result.Success(items)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun deleteApparel(apparelId: String): Result<Unit> {
        return try {
            val snapshot = firestore.collection("apparel").document(apparelId).delete().await()
            Result.Success(Unit)
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
            val updatedApparel = mapOf(
                "title" to title,
                "description" to description,
                "imageUrl" to imageUrl,
                "price" to price,
                "currency" to currency
            )
            firestore.collection("apparel").document(apparelId).update(updatedApparel).await()
            Result.Success(Unit)
        } catch (e: Exception){
            Result.Failure(e)
        }
    }

}