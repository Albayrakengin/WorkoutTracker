package com.hub.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WorkoutConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromExerciseList(value: List<String>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toExerciseList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return try {
            gson.fromJson(value, listType) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
} 