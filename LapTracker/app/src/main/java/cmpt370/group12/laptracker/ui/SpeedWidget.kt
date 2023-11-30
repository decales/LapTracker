package cmpt370.group12.laptracker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import cmpt370.group12.laptracker.presentation.MapState

class SpeedWidget {
    val speed = MapState().currentLocation?.speed
    @Composable
    fun SpeedPaceWidget () {
        Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
            Text(
                    text = "Speed: $speed",
                    style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 50.sp,
                            //fontWeight = FontWeight.Bold
                    )
            )
        }
    }
}