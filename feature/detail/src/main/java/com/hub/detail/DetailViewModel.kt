package com.hub.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hub.data.model.Exercise
import com.hub.data.model.Workout
import com.hub.data.repository.ExerciseRepository
import com.hub.data.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val exerciseRepository: ExerciseRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val workoutId: String = checkNotNull(savedStateHandle["workoutId"])
    
    private val _workout = MutableStateFlow<Workout?>(null)
    val workout: StateFlow<Workout?> = _workout
    
    val exercises: Flow<List<Exercise>> = exerciseRepository.getExercises(workoutId)

    init {
        loadWorkout()
        syncExercises()
    }

    private fun loadWorkout() {
        viewModelScope.launch {
            try {
                _workout.value = workoutRepository.getWorkout(workoutId)
            } catch (e: Exception) {
                e.printStackTrace()
                // TODO: Hata durumunu UI'da göster
            }
        }
    }

    private fun syncExercises() {
        viewModelScope.launch {
            try {
                exerciseRepository.syncExercises(workoutId)
            } catch (e: Exception) {
                e.printStackTrace()
                // TODO: Hata durumunu UI'da göster
            }
        }
    }

    fun updateWorkout(workout: Workout) {
        viewModelScope.launch {
            try {
                workoutRepository.updateWorkout(workout)
                _workout.value = workout
            } catch (e: Exception) {
                e.printStackTrace()
                // TODO: Hata durumunu UI'da göster
            }
        }
    }

    fun createExercise(exercise: Exercise) {
        viewModelScope.launch {
            try {
                exerciseRepository.createExercise(exercise)
            } catch (e: Exception) {
                e.printStackTrace()
                // TODO: Hata durumunu UI'da göster
            }
        }
    }

    fun updateExercise(exercise: Exercise) {
        viewModelScope.launch {
            try {
                exerciseRepository.updateExercise(exercise)
            } catch (e: Exception) {
                e.printStackTrace()
                // TODO: Hata durumunu UI'da göster
            }
        }
    }

    fun deleteExercise(id: String) {
        viewModelScope.launch {
            try {
                exerciseRepository.deleteExercise(id)
            } catch (e: Exception) {
                e.printStackTrace()
                // TODO: Hata durumunu UI'da göster
            }
        }
    }
} 