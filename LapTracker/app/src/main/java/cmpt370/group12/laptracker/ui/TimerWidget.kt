package cmpt370.group12.laptracker.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay

class TimerWidget {

    @Composable
    fun TimeWidget() {

        //val timerViewModel = TimerViewModel()
        val timerViewModel = viewModel<TimerViewModel>()
        LaunchedEffect(Unit) {
            timerViewModel.startTimer()
        }

        Text(
                text = formatElapsedTime(timerViewModel.elapsedTime),
                style = MaterialTheme.typography.bodyLarge.copy(),
                modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
        )
    }

    //format the time into readable text 00:00:00:000
    @Composable
    fun formatElapsedTime ( elapsedTime: State<Long>): String {
        val time = elapsedTime.value
        val miliseconds = (time % 1000).toInt()
        val seconds = (time / 1000).toInt()
        val minutes = seconds / 60
        val hours = minutes / 60
        return String.format( "%02d:%02d:%02d:%03d", hours, minutes % 60, seconds % 60, miliseconds)
    }

    @Composable
    fun TimerViewModel(): TimerViewModel {

        val viewModel = TimerViewModel()

        return viewModel
    }

}


