package cmpt370.group12.laptracker.ui.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.height
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.maps.android.compose.MarkerState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")

@Composable
fun MapScreen(

    viewModel: MapsViewModel = viewModel()
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
        Column(){
           Row(){ Text(text = "bbbbbbbbbbbbbbbbbcvccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc")}
           Row() {

            GoogleMap(
                modifier = Modifier
                    .height(500.dp),

                properties = viewModel.mapstate.value.properties,
                uiSettings = viewModel.mapstate.value.uiSettings,
                onMapLongClick = {
                    viewModel.onEvent(AppEvent.OnMapLongClick(it))
                }
            ) {

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
        }}

    }
}