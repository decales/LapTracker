package cmpt370.group12.laptracker.view.composables

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cmpt370.group12.laptracker.presentation.AppEvent
import cmpt370.group12.laptracker.viewmodel.GlobalViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.currentCameraPositionState
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")

@Composable
fun MapScreen(
    viewModel: GlobalViewModel
) {
   // var polypoints: MutableList<LatLng> = mutableListOf()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(54.7,-106.3), 15f)
    }
    if (cameraPositionState.isMoving) {
        viewModel.Set_isFollowMe(false)
    }

    Box {

        Column {
            Row {

                Row {




                    GoogleMap(
                        modifier = Modifier
                            .fillMaxSize(),

                        properties = viewModel.mapstate.value.properties,
                        uiSettings = viewModel.mapstate.value.uiSettings,
                        cameraPositionState = currentCameraPositionState,
                        onMapLongClick = {
                            viewModel.onEvent(AppEvent.OnMapLongClick(it))
                        }
                    )
                    {
                        MapEffect(viewModel.mapstate.value.currentLocation){map ->
                            map.setOnMapLoadedCallback {
                                Log.d("KRIS","GOT HERE")

                                if (viewModel.mapstate.value.isFollowMe) {
                                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(((viewModel.mapstate.value.currentLocation?.latitude?:52.0) -0.001),viewModel.mapstate.value.currentLocation?.longitude?:-106.0),cameraPositionState.position.zoom))
                                }
                                else
                                {

                                }

                            }
                        }
                        Marker(
                            state = MarkerState(
                                LatLng(
                                    viewModel.mapstate.value.currentLocation?.latitude!!,
                                    viewModel.mapstate.value.currentLocation?.longitude!!
                                )
                            ),
                            title = "ME",
                            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                            //icon = Icon(Icons.Filled.Lock, "")
                        )

                        if (viewModel.appstate.value.isMapLoaded) {
                            viewModel.trackstate.value.mapPoints.forEach { mappoint ->
                               // polypoints.add (LatLng(mappoint.latitude,mappoint.longitude))
                                Marker(
                                    state = MarkerState(
                                        LatLng(
                                            mappoint.latitude,
                                            mappoint.longitude
                                        )
                                    ),
                                    title = "(${mappoint.latitude}, ${mappoint.longitude})",
                                    snippet = "Long click to delete",
                                    onInfoWindowLongClick = {
                                        viewModel.onEvent(
                                            AppEvent.OnInfoWindowLongClick(mappoint)
                                        )
                                    },
                                    onClick = {
                                        it.showInfoWindow()
                                        true
                                    },
                                    icon = BitmapDescriptorFactory.defaultMarker(
                                        BitmapDescriptorFactory.HUE_GREEN
                                    )
                                )
                            }

                          //  Polygon(points = polypoints )
                        }
                    }
                }
            }

        }
        FloatingActionButton(modifier = Modifier
            .padding(all = 16.dp)
            .align(alignment = Alignment.TopEnd),
            onClick = {
                viewModel.Set_isFollowMe(!viewModel.mapstate.value.isFollowMe)
            }) {
            if (viewModel.mapstate.value.isFollowMe) {
                Icon(Icons.Filled.Lock, "")
            } else {
                Icon(Icons.Filled.LockOpen, "")
            }

        }
    }
}



