package cmpt370.group12.laptracker.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {

    var elapsedTimeMut = mutableStateOf(0L)
    val elapsedTime: State<Long> get() = elapsedTimeMut
    private var isRunningState = mutableStateOf(false)
    private var startTime = 0L

    init {
        startTimer()
    }

    fun startTimer() {

        if (!isRunningState.value) {

            startTime = System.currentTimeMillis()
            viewModelScope.launch {

                while (isRunningState.value) {

                    delay(100)
                    val elapsedTime = System.currentTimeMillis() - startTime
                    elapsedTimeMut.value = elapsedTime
                }
            }
            isRunningState.value = true
        }
    }
    fun stopTimer() {
        isRunningState.value = false
    }

    fun resetTimer() {
        isRunningState.value = false
        elapsedTimeMut.value = 0L
    }

    fun setTime(elapsedTime: Long) {
        elapsedTimeMut.value = elapsedTime
    }
}