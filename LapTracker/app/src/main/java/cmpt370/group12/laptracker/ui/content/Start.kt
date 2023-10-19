package cmpt370.group12.laptracker.ui.content

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cmpt370.group12.laptracker.LocationClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


@Composable
fun StartView(activity: Activity) {
    val locationClient = LocationClient(activity.applicationContext, activity, LocationServices.getFusedLocationProviderClient(activity))
    val points = remember { mutableStateListOf<Pair<Double,Double>>() } // Remember list of points
    var setToggle by remember { mutableStateOf(false) } // Remember set button toggle

    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box ( // Container to display location points
                modifier = Modifier
                    .padding(bottom = 100.dp)
            ) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    points.forEach { point ->
                        Text(text = point.toString())
                    }
                }
            }
            ToggleSetPointsButton(locationClient, points, setToggle) { setToggle = !setToggle }

            if (points.isNotEmpty() && !setToggle) {
               TrackingButton(locationClient, points, activity)
            }
        }
    }
}

@Composable
fun ToggleSetPointsButton(
    locationClient: LocationClient,
    points: SnapshotStateList<Pair<Double, Double>>,
    setToggle: Boolean,
    onClick: () -> Unit
) {
    val text = if (points.isEmpty() && !setToggle) "Set points" else if (!setToggle) "Edit points" else "Done"
    //val color = if (!setToggle) Color.Blue else Color.Red

    Button(onClick = onClick) {
        Text(text = text)
    }
    if (setToggle) { // When button is toggled on, display another button to set (and undo) a point
        SetPointButton(locationClient, points)
    }
}


@Composable
fun SetPointButton(
    locationClient: LocationClient,
    points: SnapshotStateList<Pair<Double, Double>>
) {
    val scope = CoroutineScope(Dispatchers.Main)
    var isLoading by remember { mutableStateOf(false) }

    Button( // Set a new point and add it to points array
        onClick = {
            isLoading = true
            scope.launch {
                points.add(locationClient.getAverageLocation(locationClient.getLocationFlow(1.0), 5))
                isLoading = false
            }
        }
    ) {
        Text(text = "Set point")
    }
    if (points.isNotEmpty()) {
        Button( // Remove last point entry from points array
            onClick = { points.removeLast() }
        ) {
            Text(text = "Undo")
        }
    }
    if (isLoading) {
        Text (
            modifier = Modifier
                .padding(top = 20.dp),
            text = "Setting point...")
    }
}

@Composable
fun TrackingButton(
    locationClient: LocationClient,
    points: SnapshotStateList<Pair<Double, Double>>,
    activity: Activity
) {
    var isToggled by remember { mutableStateOf(false) }
    val color = if (!isToggled) Color(0, 153, 0) else Color(179, 0, 89)
    val text = if (!isToggled) "Start Tracking" else "Stop Tracking"
    var thread by remember { mutableStateOf<Job?>(null) }
    val scope = rememberCoroutineScope()

    Button(onClick = {
        isToggled = !isToggled
        if (isToggled) {
            thread = scope.launch {
                locationClient.checkProximity(locationClient.getLocationFlow(0.5), points[0], activity.applicationContext)
            }
        }
        else {
            thread?.cancel()
        }


    }) {
        Text(text = text, color = color)
    }
}


