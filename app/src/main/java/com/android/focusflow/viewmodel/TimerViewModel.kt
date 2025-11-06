package com.android.focusflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class TimerState {
    IDLE, RUNNING, PAUSED, COMPLETED
}

enum class TimerMode {
    WORK, BREAK
}

@HiltViewModel
class TimerViewModel @Inject constructor() : ViewModel() {

    private val WORK_DURATION = 25 * 60 * 1000L // 25 minutes in milliseconds
    private val BREAK_DURATION = 5 * 60 * 1000L // 5 minutes in milliseconds

    private val _timerState = MutableStateFlow(TimerState.IDLE)
    val timerState: StateFlow<TimerState> = _timerState.asStateFlow()

    private val _timerMode = MutableStateFlow(TimerMode.WORK)
    val timerMode: StateFlow<TimerMode> = _timerMode.asStateFlow()

    private val _remainingTime = MutableStateFlow(WORK_DURATION)
    val remainingTime: StateFlow<Long> = _remainingTime.asStateFlow()

    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress.asStateFlow()

    private var timerJob: Job? = null
    private var startTime: Long = 0
    private var pausedTime: Long = 0

    fun startTimer() {
        if (_timerState.value == TimerState.IDLE || _timerState.value == TimerState.PAUSED) {
            val wasPaused = _timerState.value == TimerState.PAUSED
            _timerState.value = TimerState.RUNNING
            
            if (wasPaused) {
                _remainingTime.value = pausedTime
            }
            
            startTime = System.currentTimeMillis()
            val initialRemaining = _remainingTime.value

            timerJob = viewModelScope.launch {
                while (_remainingTime.value > 0 && _timerState.value == TimerState.RUNNING) {
                    delay(100)
                    val elapsed = System.currentTimeMillis() - startTime
                    val newRemaining = (initialRemaining - elapsed).coerceAtLeast(0)
                    _remainingTime.value = newRemaining

                    val totalDuration = if (_timerMode.value == TimerMode.WORK) WORK_DURATION else BREAK_DURATION
                    _progress.value = 1f - (newRemaining.toFloat() / totalDuration.toFloat())

                    if (newRemaining <= 0) {
                        completeTimer()
                        break
                    }
                }
            }
        }
    }

    fun pauseTimer() {
        if (_timerState.value == TimerState.RUNNING) {
            _timerState.value = TimerState.PAUSED
            pausedTime = _remainingTime.value
            timerJob?.cancel()
        }
    }

    fun resetTimer() {
        timerJob?.cancel()
        _timerState.value = TimerState.IDLE
        _remainingTime.value = if (_timerMode.value == TimerMode.WORK) WORK_DURATION else BREAK_DURATION
        _progress.value = 0f
        pausedTime = 0
    }

    private fun completeTimer() {
        _timerState.value = TimerState.COMPLETED
        timerJob?.cancel()
        
        // Switch mode for next session
        viewModelScope.launch {
            delay(1000)
            if (_timerMode.value == TimerMode.WORK) {
                _timerMode.value = TimerMode.BREAK
                _remainingTime.value = BREAK_DURATION
            } else {
                _timerMode.value = TimerMode.WORK
                _remainingTime.value = WORK_DURATION
            }
            _progress.value = 0f
            _timerState.value = TimerState.IDLE
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}

