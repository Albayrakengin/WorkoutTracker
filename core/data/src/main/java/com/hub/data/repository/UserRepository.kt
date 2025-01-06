package com.hub.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hub.data.model.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface UserRepository {
    suspend fun getCurrentUser(): User?
    suspend fun updateUserProfile(user: User)
    fun getUserFlow(): Flow<User?>
}

class DefaultUserRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : UserRepository {
    
    private val usersCollection = firestore.collection("users")
    
    override suspend fun getCurrentUser(): User? {
        val userId = auth.currentUser?.uid ?: return null
        return try {
            usersCollection.document(userId).get().await().toObject(User::class.java)
        } catch (e: Exception) {
            null
        }
    }
    
    override suspend fun updateUserProfile(user: User) {
        val userId = auth.currentUser?.uid ?: throw IllegalStateException("User not logged in")
        usersCollection.document(userId).set(user).await()
    }
    
    override fun getUserFlow(): Flow<User?> = callbackFlow {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val subscription = usersCollection.document(userId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    trySend(snapshot?.toObject(User::class.java))
                }
            
            awaitClose { subscription.remove() }
        } else {
            trySend(null)
            close()
        }
    }
} 