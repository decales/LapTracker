package cmpt370.group12.laptracker.ui.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import androidx.lifecycle.viewmodel.compose.viewModel
import cmpt370.group12.laptracker.viewModel.Events.AppEvent
import cmpt370.group12.laptracker.viewModel.ApplicationViewModel
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")

@Composable
fun MapScreen(
    viewModel: ApplicationViewModel = viewModel()
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
                    val context = LocalContext.current
                    val scope = rememberCoroutineScope()
                    MapEffect(viewModel.mapstate.value.currentLocation){map ->
                        map.setOnMapLoadedCallback {
                            if (viewModel.mapstate.value.currentLocationFollow) {
                                map.animateCamera(CameraUpdateFactory.newLatLng(LatLng(viewModel.mapstate.value.currentLocation?.latitude?:52.0,viewModel.mapstate.value.currentLocation?.longitude?:-106.0)))
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
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
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
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ){
            me()
        }
    }
}



