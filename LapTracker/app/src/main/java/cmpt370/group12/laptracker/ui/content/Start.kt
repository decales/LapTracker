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
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import cmpt370.group12.laptracker.LocationClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


data class Point (
    val latlon: Pair<Double, Double>,
    val isPassed: Boolean,
)

@Composable
fun StartView(activity: Activity) {
    val locationClient = LocationClient(activity.applicationContext, activity, LocationServices.getFusedLocationProviderClient(activity))
    val points = remember { mutableStateListOf<Point>() } // Remember list of points
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
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(top = 10.dp)
                ) {
                    if (points.isNotEmpty()) {
                        for (i in 0 until points.size) {
                            var text = "L$i"
                            if (i == 0) {
                                text = "Start"
                            }
                            Text(
                                text = text,
                                fontSize = TextUnit(10.0F, TextUnitType(0))
                            )
                        }
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
    points: SnapshotStateList<Point>,
    setToggle: Boolean,
    onClick: () -> Unit
) {
    val text = if (points.isEmpty() && !setToggle) "Set points" else if (!setToggle) "Edit points" else "Done"

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
    points: SnapshotStateList<Point>
) {
    val scope = CoroutineScope(Dispatchers.Main)
    var isLoading by remember { mutableStateOf(false) }

    Button( // Set a new point and add it to points array
        onClick = {
            isLoading = true
            scope.launch {
                points.add(Point(locationClient.getAverageLocation(locationClient.getLocationFlow(0.5), 10), false))
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
            text = "Setting point...",
            modifier = Modifier
                .padding(top = 20.dp))
    }
}

@Composable
fun TrackingButton(
    locationClient: LocationClient,
    points: SnapshotStateList<Point>,
    activity: Activity
) {
    var isToggled by remember { mutableStateOf(false) }
    val color = if (!isToggled) Color(0, 153, 0) else Color(179, 0, 89)
    val text = if (!isToggled) "Start Tracking" else "Stop Tracking"
    var thread by remember { mutableStateOf<Job?>(null) }
    val scope = rememberCoroutineScope()
    var distance by remember { mutableDoubleStateOf(0.0) }

    Button(onClick = {
        isToggled = !isToggled
        if (isToggled) {
            thread = scope.launch {
                val proximityFlow = locationClient.checkProximityFlow(locationClient.getLocationFlow(0.5), points[0].latlon)
                proximityFlow.collect { d ->
                    distance = d
                }
            }
        }
        else {
            distance = 0.0
            thread?.cancel()
        }
    }) {
        Text(text = text, color = color)
    }

    if (distance != 0.0) {
        Text (
            text = "Target is $distance m away",
            modifier = Modifier
                .padding(top = 20.dp)
        )
    }
}


