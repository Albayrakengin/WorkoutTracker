package com.hub.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hub.database.dao.ExerciseDao
import com.hub.database.dao.WorkoutDao
import com.hub.database.entity.ExerciseEntity
import com.hub.database.converter.ExerciseConverters
import com.hub.database.converter.WorkoutConverters
import com.hub.database.entity.WorkoutEntity

@Database(
    entities = [
        WorkoutEntity::class,
        ExerciseEntity::class
    ],
    version = 1
)
@TypeConverters(ExerciseConverters::class, WorkoutConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
    abstract fun exerciseDao(): ExerciseDao
} 