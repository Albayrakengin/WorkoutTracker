package com.hub.data.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hub.data.model.Exercise
import com.hub.data.model.ExerciseHistory
import com.hub.database.entity.ExerciseEntity
import com.hub.database.entity.ExerciseHistoryEntity
import com.hub.network.model.NetworkExercise
import com.hub.network.model.NetworkExerciseHistory

private val gson = Gson()

fun NetworkExerciseHistory.asEntity() = ExerciseHistoryEntity(
    id = id,
    exerciseId = exerciseId,
    workoutId = workoutId,
    userId = userId,
    sets = sets,
    reps = reps,
    weight = weight,
    date = date,
    updatedAt = updatedAt
)

fun ExerciseHistoryEntity.asModel() = ExerciseHistory(
    id = id,
    exerciseId = exerciseId,
    workoutId = workoutId,
    userId = userId,
    sets = sets,
    reps = reps,
    weight = weight,
    date = date,
    updatedAt = updatedAt
)

fun NetworkExerciseHistory.asModel() = ExerciseHistory(
    id = id,
    exerciseId = exerciseId,
    workoutId = workoutId,
    userId = userId,
    sets = sets,
    reps = reps,
    weight = weight,
    date = date,
    updatedAt = updatedAt
)

fun NetworkExercise.asEntity() = ExerciseEntity(
    id = id,
    userId = userId,
    workoutId = workoutId,
    name = name,
    description = description,
    sets = sets,
    reps = reps,
    weight = weight,
    history = gson.toJson(history.map { it.asEntity() }),
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun ExerciseEntity.asModel(): Exercise {
    val historyList = try {
        val type = object : TypeToken<List<ExerciseHistoryEntity>>() {}.type
        gson.fromJson<List<ExerciseHistoryEntity>>(history, type) ?: emptyList()
    } catch (e: Exception) {
        emptyList()
    }
    
    return Exercise(
        id = id,
        userId = userId,
        workoutId = workoutId,
        name = name,
        description = description,
        sets = sets,
        reps = reps,
        weight = weight,
        history = historyList.map { it.asModel() },
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun NetworkExercise.asModel() = Exercise(
    id = id,
    userId = userId,
    workoutId = workoutId,
    name = name,
    description = description,
    sets = sets,
    reps = reps,
    weight = weight,
    history = history.map { it.asModel() },
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Exercise.asEntity(): ExerciseEntity {
    return ExerciseEntity(
        id = id,
        userId = userId,
        workoutId = workoutId,
        name = name,
        description = description,
        sets = sets,
        reps = reps,
        weight = weight,
        history = gson.toJson(history.map { 
            ExerciseHistoryEntity(
                id = it.id,
                exerciseId = it.exerciseId,
                workoutId = it.workoutId,
                userId = it.userId,
                sets = it.sets,
                reps = it.reps,
                weight = it.weight,
                date = it.date,
                updatedAt = it.updatedAt
            )
        }),
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Exercise.asNetworkModel(): NetworkExercise {
    return NetworkExercise(
        id = id,
        userId = userId,
        workoutId = workoutId,
        name = name,
        description = description,
        sets = sets,
        reps = reps,
        weight = weight,
        history = history.map { 
            NetworkExerciseHistory(
                id = it.id,
                exerciseId = it.exerciseId,
                workoutId = it.workoutId,
                userId = it.userId,
                sets = it.sets,
                reps = it.reps,
                weight = it.weight,
                date = it.date,
                updatedAt = it.updatedAt
            )
        },
        createdAt = createdAt,
        updatedAt = updatedAt
    )
} 