package cmpt370.group12.laptracker.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class TimerWidget {
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