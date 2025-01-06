package com.hub.network.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hub.network.model.NetworkExercise
import com.hub.network.model.NetworkWorkout
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseService @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    private val currentUserId: String?
        get() = auth.currentUser?.uid

    // Workout CRUD operations
    suspend fun createWorkout(workout: NetworkWorkout): String {
        val userId = requireNotNull(currentUserId) { "User must be authenticated" }
        val workoutRef = db.collection("users")
            .document(userId)
            .collection("workouts")
            .document()
        
        val workoutWithId = workout.copy(id = workoutRef.id, userId = userId)
        workoutRef.set(workoutWithId).await()
        return workoutRef.id
    }

    suspend fun getWorkouts(): List<NetworkWorkout> {
        val userId = requireNotNull(currentUserId) { "User must be authenticated" }
        return db.collection("users")
            .document(userId)
            .collection("workouts")
            .get()
            .await()
            .documents
            .mapNotNull { it.toObject(NetworkWorkout::class.java) }
    }

    suspend fun getWorkout(id: String): NetworkWorkout? {
        val userId = requireNotNull(currentUserId) { "User must be authenticated" }
        return db.collection("users")
            .document(userId)
            .collection("workouts")
            .document(id)
            .get()
            .await()
            .toObject(NetworkWorkout::class.java)
    }

    suspend fun updateWorkout(workout: NetworkWorkout) {
        val userId = requireNotNull(currentUserId) { "User must be authenticated" }
        require(workout.userId == userId) { "Cannot update workout belonging to another user" }
        db.collection("users")
            .document(userId)
            .collection("workouts")
            .document(workout.id)
            .set(workout)
            .await()
    }

    suspend fun deleteWorkout(id: String) {
        val userId = requireNotNull(currentUserId) { "User must be authenticated" }
        db.collection("users")
            .document(userId)
            .collection("workouts")
            .document(id)
            .delete()
            .await()
    }

    // Exercise CRUD operations
    suspend fun createExercise(exercise: NetworkExercise): String {
        val userId = requireNotNull(currentUserId) { "User must be authenticated" }
        val exerciseRef = db.collection("users")
            .document(userId)
            .collection("exercises")
            .document()
        
        val exerciseWithId = exercise.copy(id = exerciseRef.id, userId = userId)
        exerciseRef.set(exerciseWithId).await()
        return exerciseRef.id
    }

    suspend fun getExercises(workoutId: String): List<NetworkExercise> {
        val userId = requireNotNull(currentUserId) { "User must be authenticated" }
        return db.collection("users")
            .document(userId)
            .collection("exercises")
            .whereEqualTo("workoutId", workoutId)
            .get()
            .await()
            .documents
            .mapNotNull { it.toObject(NetworkExercise::class.java) }
    }

    suspend fun getExercise(id: String): NetworkExercise? {
        val userId = requireNotNull(currentUserId) { "User must be authenticated" }
        return db.collection("users")
            .document(userId)
            .collection("exercises")
            .document(id)
            .get()
            .await()
            .toObject(NetworkExercise::class.java)
    }

    suspend fun updateExercise(exercise: NetworkExercise) {
        val userId = requireNotNull(currentUserId) { "User must be authenticated" }
        require(exercise.userId == userId) { "Cannot update exercise belonging to another user" }
        db.collection("users")
            .document(userId)
            .collection("exercises")
            .document(exercise.id)
            .set(exercise)
            .await()
    }

    suspend fun deleteExercise(id: String) {
        val userId = requireNotNull(currentUserId) { "User must be authenticated" }
        db.collection("users")
            .document(userId)
            .collection("exercises")
            .document(id)
            .delete()
            .await()
    }

    suspend fun saveUserLanguage(userId: String, languageCode: String) {
        try {
            db.collection("users")
                .document(userId)
                .update("language", languageCode)
                .await()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getUserLanguage(userId: String): String? {
        return try {
            val userDoc = db.collection("users")
                .document(userId)
                .get()
                .await()
            userDoc.getString("language")
        } catch (e: Exception) {
            null
        }
    }
} 