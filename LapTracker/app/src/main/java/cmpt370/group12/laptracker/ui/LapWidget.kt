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

class LapWidget {
    @Composable
    fun LapsWidget () {
        //TODO
        //need to find a way to track completed laps or have some time of counter implements
        val lapsCompleted = 0
        Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text (
                    text = "Laps Done: $lapsCompleted",
                    style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 50.sp,
                    )
            )
        }
    }
}