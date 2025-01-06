package com.hub.data.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hub.data.model.Workout
import com.hub.database.entity.WorkoutEntity
import com.hub.network.model.NetworkWorkout

private val gson = Gson()

fun NetworkWorkout.asEntity() = WorkoutEntity(
    id = id,
    userId = userId,
    name = name,
    type = type,
    description = description,
    notes = notes,
    exercises = gson.toJson(exercises),
    lastSession = lastSession,
    imageUrl = imageUrl,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun WorkoutEntity.asModel(): Workout {
    val exercisesList = try {
        val type = object : TypeToken<List<String>>() {}.type
        gson.fromJson<List<String>>(exercises, type) ?: emptyList()
    } catch (e: Exception) {
        emptyList()
    }
    
    return Workout(
        id = id,
        userId = userId,
        name = name,
        type = type,
        description = description,
        notes = notes,
        exercises = exercisesList,
        lastSession = lastSession,
        imageUrl = imageUrl,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun NetworkWorkout.asModel() = Workout(
    id = id,
    userId = userId,
    name = name,
    type = type,
    description = description,
    notes = notes,
    exercises = exercises,
    lastSession = lastSession,
    imageUrl = imageUrl,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Workout.asEntity() = WorkoutEntity(
    id = id,
    userId = userId,
    name = name,
    type = type,
    description = description,
    notes = notes,
    exercises = gson.toJson(exercises),
    lastSession = lastSession,
    imageUrl = imageUrl,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Workout.asNetworkModel() = NetworkWorkout(
    id = id,
    userId = userId,
    name = name,
    type = type,
    description = description,
    notes = notes,
    exercises = exercises,
    lastSession = lastSession,
    imageUrl = imageUrl,
    createdAt = createdAt,
    updatedAt = updatedAt
) 