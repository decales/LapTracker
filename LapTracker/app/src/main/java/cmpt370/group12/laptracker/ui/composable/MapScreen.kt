package cmpt370.group12.laptracker.ui.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import cmpt370.group12.laptracker.viewModel.Events.AppEvent
import cmpt370.group12.laptracker.viewModel.ApplicationViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState




@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")

@Composable
fun MapScreen(viewModel: ApplicationViewModel

) {



    val cameraPositionState = rememberCameraPositionState()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,

    ) {
        Column(

        ) {
            if(viewModel.mapstate.value.isVisible) {
                GoogleMap(

                    modifier = Modifier
                        .fillMaxSize(),

                    properties = viewModel.mapstate.value.properties,
                    uiSettings = viewModel.mapstate.value.uiSettings,
                    onMapLongClick = {
                        viewModel.onEvent(AppEvent.OnMapLongClick(it))
                    },


                )
                {

                    MapEffect(viewModel.mapstate.value.currentLocation){map ->
                        map.setOnMapLoadedCallback {
                            if (viewModel.mapstate.value.currentLocationFollow) {
                                map.animateCamera(CameraUpdateFactory.newLatLng(LatLng(viewModel.mapstate.value.currentLocation?.latitude?:52.0,viewModel.mapstate.value.currentLocation?.longitude?:-106.0)))
                                 }
                                else
                            {

                            }

                        }
                    }

                    Marker(
                        state = MarkerState(
                            LatLng(
                                viewModel.mapstate.value.currentLocation?.latitude?:52.0,
                                viewModel.mapstate.value.currentLocation?.longitude?:-106.0
                            )
                        ),
                        title = "ME",
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)
                    )

                    viewModel.trackstate.value.mapPoints.forEach { mappoint ->
                        Marker(
                            state = MarkerState(LatLng(mappoint.latitude, mappoint.longitude)),
                            title = "(${mappoint.latitude}, ${mappoint.longitude})",
                            snippet = "Long click to delete",
                            onInfoWindowLongClick =
                            {
                                viewModel.onEvent(
                                    AppEvent.OnInfoWindowLongClick(mappoint)
                                )
                            },
                            onClick =
                            {
                                it.showInfoWindow()
                                true
                            },

                            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                        )
                    }

                }
            }
        }
        Column(
            Modifier.fillMaxSize().padding(10.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            if (viewModel.trackstate.value.isRunPaused){
                FloatingActionButton(
                    modifier = Modifier
                        .size(125.dp),
                    onClick = {viewModel.UnpauseRun()},
                ) {
                    Icon(Icons.Filled.PlayArrow, contentDescription = "", modifier = Modifier.fillMaxSize(0.5f))
                }
            }
            else {
                FloatingActionButton(
                    modifier = Modifier
                        .size(125.dp),
                    onClick = {viewModel.pauseRun()},
                ) {
                    Icon(Icons.Filled.Pause, contentDescription = "", modifier = Modifier.fillMaxSize(0.75f))

                }

            }

            RealTimeStatisticsContainer(viewModel)
        }
    }
}



