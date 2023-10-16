package cmpt370.group12.laptracker.ui.content

import android.location.Location
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cmpt370.group12.laptracker.LocationClient
import cmpt370.group12.laptracker.MainActivity
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest


@Composable
fun StartView(activity: MainActivity) {

    val locationClient = LocationClient(activity.applicationContext, activity, LocationServices.getFusedLocationProviderClient(activity))

    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            ToggleTrackingButton(locationClient)
        }
    }
}


@Composable
fun ToggleTrackingButton(locationClient: LocationClient) {

    var isClicked by remember { mutableStateOf(false) }
    val text = if (!isClicked) "Start tracking" else "Stop tracking"
    val color = if (!isClicked) Color(10, 120, 40) else Color(190, 0, 0)

    Row {
        Button(onClick = { isClicked = !isClicked })
        {
            Text(text = text, color = color)
        }

        if (isClicked) {
            Button(onClick = {
                // TODO

            }) {
                Text(text = "Log location")
            }
        }
    }
}

//@Composable
//fun LogLocationButton(locationFlow: Flow<Location?>?) {
//    var isClicked by remember { mutableStateOf(false) }
//
//    Button( onClick = { isClicked = true }
//    ) {
//        Text(text = "Log location")
//    }
//
//}

