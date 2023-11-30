package cmpt370.group12.laptracker.ui

import android.annotation.SuppressLint
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

class DistanceWidget {

    @SuppressLint("NotConstructor")
    @Composable
    fun DistanceWidget () {

        val tracker = DistanceCalc()
        val local = MapState().currentLocation
        tracker.update(local)
        val distance = tracker.getDistance()
        Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text (
                    text = "Distance: $distance",
                    style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 50.sp,
                    )
            )
        }
    }
}