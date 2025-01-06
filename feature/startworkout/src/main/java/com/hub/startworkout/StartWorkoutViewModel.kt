package com.hub.startworkout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hub.data.model.Exercise
import com.hub.data.model.ExerciseHistory
import com.hub.data.model.Workout
import com.hub.data.repository.ExerciseRepository
import com.hub.data.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WorkoutState(
    val currentExerciseIndex: Int = 0,
    val currentSets: Int = 1,
    val currentReps: Int = 8,
    val currentWeight: Float = 0f,
    val showExitDialog: Boolean = false,
    val previousExerciseHistory: ExerciseHistory? = null
)

sealed interface StartWorkoutEvent {
    data class UpdateSets(val sets: Int) : StartWorkoutEvent
    data class UpdateReps(val reps: Int) : StartWorkoutEvent
    data class UpdateWeight(val weight: Float) : StartWorkoutEvent
    data object NextExercise : StartWorkoutEvent
    data object PreviousExercise : StartWorkoutEvent
    data object ShowExitDialog : StartWorkoutEvent
    data object HideExitDialog : StartWorkoutEvent
    data object FinishWorkout : StartWorkoutEvent
}

@HiltViewModel
class StartWorkoutViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val exerciseRepository: ExerciseRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val workoutId: String = checkNotNull(savedStateHandle["workoutId"])
    
    private val _workout = MutableStateFlow<Workout?>(null)
    val workout: StateFlow<Workout?> = _workout
    
    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> = _exercises

    var state by mutableStateOf(WorkoutState())
        private set

    val currentExercise: Exercise? get() = exercises.value.getOrNull(state.currentExerciseIndex)

    val hasNextExercise: Boolean get() = state.currentExerciseIndex < exercises.value.size - 1
    val hasPreviousExercise: Boolean get() = state.currentExerciseIndex > 0

    init {
        loadWorkout()
        loadExercises()
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

    private fun loadExercises() {
        viewModelScope.launch {
            try {
                exerciseRepository.getExercises(workoutId).collectLatest { exercises ->
                    _exercises.value = exercises
                    if (exercises.isNotEmpty() && currentExercise != null) {
                        val lastHistory = currentExercise?.history?.maxByOrNull { it.date }
                        state = state.copy(
                            currentSets = lastHistory?.sets ?: currentExercise?.sets ?: 1,
                            currentReps = lastHistory?.reps ?: currentExercise?.reps ?: 8,
                            currentWeight = lastHistory?.weight ?: currentExercise?.weight ?: 0f,
                            previousExerciseHistory = lastHistory
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onEvent(event: StartWorkoutEvent) {
        when (event) {
            StartWorkoutEvent.ShowExitDialog -> {
                state = state.copy(showExitDialog = true)
            }
            StartWorkoutEvent.HideExitDialog -> {
                state = state.copy(showExitDialog = false)
            }
            StartWorkoutEvent.FinishWorkout -> {
                saveCurrentExercise()
                updateWorkoutLastSession()
            }
            StartWorkoutEvent.NextExercise -> {
                if (hasNextExercise) {
                    saveCurrentExercise()
                    state = state.copy(
                        currentExerciseIndex = state.currentExerciseIndex + 1
                    )
                    currentExercise?.let { exercise ->
                        val lastHistory = exercise.history.maxByOrNull { it.date }
                        state = state.copy(
                            currentSets = lastHistory?.sets ?: exercise.sets,
                            currentReps = lastHistory?.reps ?: exercise.reps,
                            currentWeight = lastHistory?.weight ?: exercise.weight,
                            previousExerciseHistory = lastHistory
                        )
                    }
                }
            }
            StartWorkoutEvent.PreviousExercise -> {
                if (hasPreviousExercise) {
                    saveCurrentExercise()
                    state = state.copy(
                        currentExerciseIndex = state.currentExerciseIndex - 1
                    )
                    currentExercise?.let { exercise ->
                        val lastHistory = exercise.history.maxByOrNull { it.date }
                        state = state.copy(
                            currentSets = lastHistory?.sets ?: exercise.sets,
                            currentReps = lastHistory?.reps ?: exercise.reps,
                            currentWeight = lastHistory?.weight ?: exercise.weight,
                            previousExerciseHistory = lastHistory
                        )
                    }
                }
            }
            is StartWorkoutEvent.UpdateSets -> {
                state = state.copy(currentSets = event.sets)
            }
            is StartWorkoutEvent.UpdateReps -> {
                state = state.copy(currentReps = event.reps)
            }
            is StartWorkoutEvent.UpdateWeight -> {
                state = state.copy(currentWeight = event.weight)
            }
        }
    }

    private fun saveCurrentExercise() {
        viewModelScope.launch {
            try {
                currentExercise?.let { exercise ->
                    val newHistory = ExerciseHistory(
                        id = java.util.UUID.randomUUID().toString(),
                        exerciseId = exercise.id,
                        workoutId = workoutId,
                        userId = exercise.userId,
                        sets = state.currentSets,
                        reps = state.currentReps,
                        weight = state.currentWeight
                    )

                    val updatedHistory = exercise.history + newHistory

                    exerciseRepository.updateExercise(
                        exercise.copy(
                            sets = state.currentSets,
                            reps = state.currentReps,
                            weight = state.currentWeight,
                            history = updatedHistory,
                            updatedAt = System.currentTimeMillis()
                        )
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun updateWorkoutLastSession() {
        viewModelScope.launch {
            try {
                _workout.value?.let { currentWorkout ->
                    val lastSession = System.currentTimeMillis().toString()
                    workoutRepository.updateWorkout(
                        currentWorkout.copy(
                            lastSession = lastSession,
                            updatedAt = System.currentTimeMillis()
                        )
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // TODO: Hata durumunu UI'da göster
            }
        }
    }
} 