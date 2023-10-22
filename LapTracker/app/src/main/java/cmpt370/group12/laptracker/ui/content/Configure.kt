package cmpt370.group12.laptracker.ui.content

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.sourceInformation
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cmpt370.group12.laptracker.LocationClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun ConfigureView(activity: Activity) {
    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        val locationClient = LocationClient(activity.applicationContext, activity, LocationServices.getFusedLocationProviderClient(activity))
        val scope = CoroutineScope(Dispatchers.Main)

        Box (
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column {
                Button(onClick = {
                    scope.launch {
                        println(locationClient.getCurrentLocation())
                    }
                }) {
                    Text(text = "Start")
                }
            }
        }
    }
}