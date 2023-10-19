package cmpt370.group12.laptracker.ui.content

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import kotlinx.coroutines.launch


@Composable
fun StartView(activity: Activity) {
    val locationClient = LocationClient(activity.applicationContext, activity, LocationServices.getFusedLocationProviderClient(activity))

    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            ToggleSetPointsButton(locationClient)
            
            Text(text = "backdoor: 52.129813, -106.633604")
            Text(text = "bench: 52.129874, -106.632387")
            Text(text = "Path fork: 52.131800, -106.632115")
        }
        
    }
}


@Composable
fun ToggleSetPointsButton(locationClient: LocationClient) {

    var isClicked by remember { mutableStateOf(false) }
    val text = if (!isClicked) "Set points" else "Done"
    val color = if (!isClicked) Color(10, 120, 40) else Color(190, 0, 0)
    val pointsSnapshot = remember { mutableStateListOf<Pair<Double,Double>>() } // Snapshot of current state of points list
    val points: List<Pair<Double, Double>> = pointsSnapshot // List of saved legs, <longitude, latitude>

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
        Button(onClick = { isClicked = !isClicked }
        ) {
            Text(text = text, color = color)
        }
        if (isClicked) { // When button is toggled on, display another button to set (and undo) a point
            SetPointButton(locationClient, pointsSnapshot)
        }
    }
}


@Composable
fun SetPointButton(locationClient: LocationClient, legs: SnapshotStateList<Pair<Double, Double>>) {
    val scope = CoroutineScope(Dispatchers.Main)
    var isLoading by remember { mutableStateOf(false) }

    Button( // Set a new point and add it to points array
        onClick = {
            isLoading = true
            scope.launch {
                legs.add(locationClient.getAverageLocation(locationClient.getLocationFlow(1.0), 5))
                isLoading = false
            }
        }
    ) {
        Text(text = "Set point")
    }
    if (legs.isNotEmpty()) {
        Button( // Remove last point entry from points array
            onClick = { legs.removeLast() }
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


