package cmpt370.group12.laptracker
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.unit.dp
import cmpt370.group12.laptracker.ui.theme.LapTrackerTheme
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Feature2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var loc = LocationClient(this.applicationContext, this, LocationServices.getFusedLocationProviderClient(this))
        setContent {
            LapTrackerTheme {
                // A surface container using the 'background' color from the theme
                Spacer(modifier = Modifier.padding(1.dp))
                mainScreen(loc)
            }
        }
    }
}

@Composable
fun mainScreen(locationClient: LocationClient) {
    val points = remember { mutableStateListOf<Pair<Double,Double>>() } // Remember list of points
    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box( // Container to display location points
                modifier = Modifier
                    .padding(bottom = 100.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    points.forEach { point ->
                        Text(text = point.toString())
                    }
                }
            }
            getAnotherSegmentButton(locationClient, points)
        }
    }
}

@Composable
fun getAnotherSegmentButton(locationClient: LocationClient, points: SnapshotStateList<Pair<Double, Double>>) {
    val scope = CoroutineScope(Dispatchers.Main)
    var isLoading by remember { mutableStateOf(false) }
    isLoading = true
    Button( // Set a new point and add it to points array
        onClick = {
            isLoading = true
            scope.launch {
                points.add(locationClient.getAverageLocation(locationClient.getLocationFlow(1.0), 6))
                isLoading = false
            }
        }
    ) {
        Text("Set a new Segment?")
    }

    Button(
        onClick = {

        }
    ) {
        Text("Finished")
    }
}