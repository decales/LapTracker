package cmpt370.group12.laptracker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerWidget {

    @Composable
    fun TimeWidget() {
        var isRunning by remember { mutableStateOf(false) }
        var elapsedTime by remember { mutableStateOf(0L) }
        var startTime by remember { mutableStateOf(0L) }
        val scope = rememberCoroutineScope()
        Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                    text = formatElapsedTime(elapsedTime),
                    style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 50.sp, // Adjust the font size as needed
                            //fontWeight = FontWeight.Bold
                    )
            )
            Spacer ( modifier = Modifier.height(16.dp))
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TimeWidgetButton(
                        text = if (isRunning) "Stop" else "Start",
                        onClick = {
                            if (isRunning) {
                                isRunning = false
                            } else {
                                isRunning = true
                                startTime = System.currentTimeMillis() - elapsedTime
                                scope.launch {
                                    while (isRunning) {
                                        elapsedTime = System.currentTimeMillis() - startTime
                                        delay(1000)
                                    }
                                }
                            }
                        },
                        enabled = true
                )
                TimeWidgetButton(
                        text = "Reset",
                        onClick = {
                            isRunning = false
                            elapsedTime = 0
                        },
                        enabled = !isRunning
                )
            }
        }
    }


    //format the time into readable text 00:00:00:000
    @Composable
    fun formatElapsedTime ( elapsedTime: Long): String {

        val miliseconds = (elapsedTime % 1000).toInt()
        val seconds = (elapsedTime / 1000).toInt()
        val minutes = seconds / 60
        val hours = minutes / 60

        return String.format( "%02d:%02d:%02d:%03d", hours, minutes % 60, seconds % 60, miliseconds)
    }

    @Composable
    fun TimeWidgetButton ( text: String, onClick: () -> Unit, enabled: Boolean) {

        Button(
                onClick = onClick,
                modifier = Modifier.padding(8.dp),
                enabled = enabled
        ) {
            Text(text)
        }
    }
}