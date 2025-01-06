package com.hub.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hub.data.model.Workout
import com.hub.data.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository
) : ViewModel() {
    
    val workouts: Flow<List<Workout>> = workoutRepository.getWorkouts()

    init {
        syncWorkouts()
    }

    private fun syncWorkouts() {
        viewModelScope.launch {
            try {
                workoutRepository.syncWorkouts()
            } catch (e: Exception) {
                e.printStackTrace()
                // TODO: Hata durumunu UI'da göster
            }
        }
    }

    fun createWorkout(workout: Workout) {
        viewModelScope.launch {
            try {
                workoutRepository.createWorkout(workout)
            } catch (e: Exception) {
                e.printStackTrace()
                // TODO: Hata durumunu UI'da göster
            }
        }
    }

    fun deleteWorkout(id: String) {
        viewModelScope.launch {
            try {
                workoutRepository.deleteWorkout(id)
            } catch (e: Exception) {
                e.printStackTrace()
                // TODO: Hata durumunu UI'da göster
            }
        }
    }
}