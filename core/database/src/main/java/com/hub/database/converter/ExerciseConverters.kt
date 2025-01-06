package com.hub.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hub.database.entity.ExerciseHistoryEntity

class ExerciseConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromHistoryList(value: List<ExerciseHistoryEntity>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toHistoryList(value: String): List<ExerciseHistoryEntity> {
        val listType = object : TypeToken<List<ExerciseHistoryEntity>>() {}.type
        return try {
            gson.fromJson(value, listType) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
} 