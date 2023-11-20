package cmpt370.group12.laptracker.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import cmpt370.group12.laptracker.viewmodel.GlobalViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")

@Composable
fun MapScreen(
    viewModel: GlobalViewModel = viewModel()
) {


    //val scaffoldState = rememberScaffoldState()

    Scaffold(
        //scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AppEvent.ToggleMap)
            }) {

            }
        }
    ) {
        Column() {
            Row() {

                Row() {

                    GoogleMap(
                        modifier = Modifier
                            .fillMaxSize(),

                        properties = viewModel.mapstate.value.properties,
                        uiSettings = viewModel.mapstate.value.uiSettings,
                        onMapLongClick = {
                            viewModel.onEvent(AppEvent.OnMapLongClick(it))
                        }
                    )
                    {

                        Marker(
                            state = MarkerState(
                                LatLng(
                                    viewModel.mapstate.value.currentLocation?.latitude!!,
                                    viewModel.mapstate.value.currentLocation?.longitude!!
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

                    }
                }
            }

        }
    }
}



