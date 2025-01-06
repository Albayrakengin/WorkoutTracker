package com.hub.data.di

import com.hub.data.repository.DefaultExerciseRepository
import com.hub.data.repository.DefaultWorkoutRepository
import com.hub.data.repository.ExerciseRepository
import com.hub.data.repository.WorkoutRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindWorkoutRepository(
        defaultWorkoutRepository: DefaultWorkoutRepository
    ): WorkoutRepository

    @Binds
    @Singleton
    abstract fun bindExerciseRepository(
        defaultExerciseRepository: DefaultExerciseRepository
    ): ExerciseRepository
} 