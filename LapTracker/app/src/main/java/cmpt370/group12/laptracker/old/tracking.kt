package cmpt370.group12.laptracker.old

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
import cmpt370.group12.laptracker.model.LocationClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


data class Point (
    val latlon: Pair<Double, Double>,
    val name: String,
    var isPassed: Boolean
)

@Composable
fun trackingView(activity: Activity) {
    val locationClient = LocationClient(activity)
    val points = remember { mutableStateListOf<Point>() } // Remember list of points
    var setToggle by remember { mutableStateOf(false) } // Remember set button toggle



    locationClient.getLocationPermission()

    //locationClient.startLocationFlow()



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

                    points.forEach { point ->
                        Text(text = point.name)
                    }
                }
            }
            ToggleSetPointsButton(locationClient, points, setToggle) { setToggle = !setToggle }

            if (points.isNotEmpty() && !setToggle) {
               TrackingButton(locationClient, points)
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
fun SetPointButton (
    locationClient: LocationClient,
    points: SnapshotStateList<Point>
) {
    val scope = CoroutineScope(Dispatchers.Main)
    var isLoading by remember { mutableStateOf(false) }
    val text = if (points.isEmpty()) "Set start" else "Set point"



    Button( // Set a new point and add it to points array
        onClick = {
            isLoading = true
            scope.launch {
                val pointID = if (points.isEmpty()) "Start" else "L${points.size}"
                //points.add(Point(locationClient.getAverageLocation(10), pointID,false))
                isLoading = false
            }
        }
    ) {
        Text(text = text)
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
fun TrackingButton (
    locationClient: LocationClient,
    points: SnapshotStateList<Point>,
) {
    var isToggled by remember { mutableStateOf(false) }
    val color = if (!isToggled) Color(0, 153, 0) else Color(179, 0, 89)
    val text = if (!isToggled) "Start Tracking" else "Stop Tracking"
    var thread by remember { mutableStateOf<Job?>(null) }
    val scope = rememberCoroutineScope()

    // Tracking UI variables
    var distance by remember { mutableStateOf(0.0) }
    var laps by remember { mutableStateOf(0) }
    var next by remember { mutableStateOf("")}

    Button(onClick = {

        isToggled = !isToggled

        if (isToggled) { // Start lap tracking
            thread = scope.launch {
                while (thread?.isActive == true) {
                    points.forEach { point ->
                        next = point.name
//                        locationClient.getProximityFlow(point.latlon)?.first { d -> // Emit from flow until within 2 meters
//                            distance = d // Update UI
//                            d < 2.0
//                        }
                    }
                    laps += 1 // All points have been reached, +1 lap
                }
            }
        }
        else { // Reset values and
            distance = 0.0
            laps = 0
            thread?.cancel()
        }
    }) {
        Text(text = text, color = color)
    }

    if (thread?.isActive == true) {
        Text (
            text = "$next is $distance m away",
            modifier = Modifier
                .padding(top = 20.dp)
        )
        Text(text = "Laps: $laps")
    }
}


//suspend fun trackLaps(client: LocationClient, points: SnapshotStateList<Point>) {
//    var laps = 0
//    val locationFlow = client.getLocationFlow(0.5) // Live location flow
//
//    while (true) { // TODO replace with condition to stop tracking
//        points.forEach { point ->
//            client.checkProximityFlow(locationFlow, point.latlon).collect { distance ->
//                while (!point.isPassed) {
//                    if (distance <= 5) {
//                        point.isPassed = true
//                    }
//                }
//            }
//        }
//        laps += 1
//    }
//}


