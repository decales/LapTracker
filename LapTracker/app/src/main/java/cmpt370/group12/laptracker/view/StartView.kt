package cmpt370.group12.laptracker.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cmpt370.group12.laptracker.R
import cmpt370.group12.laptracker.viewmodel.StartViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import javax.annotation.meta.When

class StartView(
    private val viewModel: StartViewModel
) {
    /* View:
        - Represent the entire start view
        - Made up of class defined composable functions
        - All data is stored retrieved from class view model */
    @Composable
    fun View() {
        // Immediately check location services
        if (!viewModel.locationClient.servicesEnabled() || !viewModel.locationClient.hasLocationPermissions()) viewModel.viewState.value = StartViewModel.ViewState.noServices
        else viewModel.viewState.value = StartViewModel.ViewState.chooseMode

        MapView()
        Column {
            Header()
            when(viewModel.viewState.value) {
                StartViewModel.ViewState.chooseMode -> ChooseModeView()
                StartViewModel.ViewState.newTrack -> NewTrackView()
                StartViewModel.ViewState.loadTrack -> LoadTrackView()
                StartViewModel.ViewState.saveTrack -> SaveTrackView()
                StartViewModel.ViewState.inRun -> InRunView()
                StartViewModel.ViewState.postRun -> PostRunView()
                StartViewModel.ViewState.noServices -> NoServicesView()
            }
        }
    }


    @Composable
    fun Header() {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp)
        )
        {
            Text(
                text = "Start Tracking",
                fontSize = 30.sp
            )
        }
    }

    @Composable
    fun ChooseModeView() {
        Card {
            Column {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "New Track")
                }
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Load Track")
                }
            }
        }
    }

    @Composable
    fun NoServicesView() {
        var refresh by remember { mutableIntStateOf(0) } // Arbitrary var that recomposes the screen upon updating
        Card {
            Column {
                Icon(
                    painter = painterResource(id = R.drawable.startview_noservices),
                    contentDescription = "startview_noservices",
                    modifier = Modifier.size(48.dp)
                )
                Text(text = refresh.toString(), color = Color.Transparent) // composable with var must be in parent composable to trigger recompose
                if (!viewModel.locationClient.servicesEnabled()) {
                    Text(text = "Location services not enabled", fontSize = 20.sp, modifier = Modifier.padding(bottom = 20.dp))
                    Text(text = "Please enable your device's cellular and/or GPS services to continue.")
                    Spacer(modifier = Modifier.weight(1.0f))
                }
                else {
                    Text(text = "Location permissions not granted", fontSize = 20.sp, modifier = Modifier.padding(bottom = 20.dp))
                    Text(text = "Please allow access to your device's precise location to continue.")
                    Spacer(modifier = Modifier.weight(1.0f))
                    Button(onClick = { viewModel.locationClient.getLocationPermission() }) {
                        Text(text = "Permissions")
                    }
                }
                Button(onClick = { refresh++}) {
                    Text(text = "Refresh")
                }
            }
        }
    }


    @Composable
    fun LoadTrackView() {

    }


    @Composable
    fun NewTrackView() {

    }


    @Composable
    fun SaveTrackView() {

    }


    @Composable
    fun InRunView() {

    }


    @Composable
    fun PostRunView() {

    }


    @Composable
    fun MapView() {
        // CameraPosition(LatLng(Random.nextDouble() * 180.0 - 90.0, 0.0), 0F, 0F, 0F)
        val cameraState by remember { mutableStateOf(CameraPositionState()) }

        Card(modifier = Modifier.padding(20.dp)) {
            GoogleMap(
                properties = viewModel.mapProperties.value,
                uiSettings = viewModel.mapSettings.value,
                cameraPositionState = cameraState,
                //onMapLongClick = { viewModel.onEvent(AppEvent.OnMapLongClick(it)) },
                modifier = Modifier
                    .fillMaxSize()
                    .blur(if (!viewModel.mapIsEnabled.value) 5.dp else 0.dp)
            ) {
                if (!viewModel.mapIsEnabled.value) LaunchedEffect(Unit) { viewModel.panMapCamera(cameraState) }

                if (viewModel.mapIsEnabled.value) {
                    Marker(
                        title = "Current location",
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED),
                        state = MarkerState(viewModel.currentLocation.value)
                    )
                    viewModel.mapPoints.forEach { point -> // Create markers for each track point
                        Marker(
                            state = MarkerState(LatLng(point.latitude, point.longitude)),
                            title = "(${point.latitude}, ${point.longitude})",
                            snippet = "Long click to delete",
//                    onInfoWindowLongClick = {
//                        viewModel.onEvent(
//                            AppEvent.OnInfoWindowLongClick(mappoint)
//                        )
//                    },
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


//    @Composable
//    fun TrackingView() {
//        Box (
//            contentAlignment = Alignment.Center,
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//            Card(
//                modifier = Modifier
//                    .align(Alignment.Center)
//                    .size(width = 250.dp, height = 350.dp)
//            ) {
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = Modifier.fillMaxSize()
//                ) {
//                    Box( // Container to display location points
//                        modifier = Modifier
//                            .padding(bottom = 10.dp)
//                            .height(100.dp)
//                            .verticalScroll(rememberScrollState())
//                            .fillMaxWidth()
//                    ) {
//                        Column(
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                            modifier = Modifier
//                                .padding(top = 10.dp)
//                                .fillMaxSize()
//                        ) {
//                            viewModel.points.forEach { point ->
//                                Text(text = point.name)
//                            }
//                        }
//                    }
//                    if (!viewModel.setToggle.value && !viewModel.trackPicked.value) {
//                        //SetPointButton(viewModel.points)
//                    }
//                    if (viewModel.points.isNotEmpty() && viewModel.setToggle.value) {
//                            TrackingButton(viewModel.points)
//                    }
//                }
//            }
//        }
//    }


//    @Composable
//    fun SetPointButton (
//        points: SnapshotStateList<Point>
//    ) {
//        viewModel.textSetPoints = if (points.isEmpty()) "Set start" else "Set point"
//        Button( // Set a new point and add it to points array
//            onClick = {
//                viewModel.isLoading.value = true
//                viewModel.scope.launch {
//                    val pointID = if (points.isEmpty()) "Start" else "L${points.size}"
//                    points.add(Point(viewModel.getAverageLocation(), pointID,false))
//                    viewModel.isLoading.value = false
//                }
//            }
//        ) {
//            Text(text = viewModel.textSetPoints)
//        }
//        if (points.isNotEmpty()) {
//            Button( // Remove last point entry from points array
//                onClick = { points.removeLast() }
//            ) {
//                Text(text = "Undo")
//            }
//            Button( // Remove last point entry from points array
//                onClick = { viewModel.setToggle.value = !viewModel.setToggle.value }
//            ) {
//                Text(text = "Done")
//            }
//        }
//        if (viewModel.isLoading.value) {
//            Text (
//                text = "Setting point...",
//                modifier = Modifier
//                    .padding(top = 20.dp))
//        }
//    }

//    @Composable
//    fun TrackListView(trackPicked: MutableState<Boolean>) {
//        Box (
//            contentAlignment = Alignment.Center,
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//            Card(
//                modifier = Modifier
//                    .align(Alignment.Center)
//                    .size(width = 250.dp, height = 350.dp)
//                    .verticalScroll(rememberScrollState())
//            ) {
//                viewModel.trackCards.forEach { track ->
//                    Card(
//                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
//                        modifier = Modifier
//                            .padding(bottom = 10.dp)
//                            .fillMaxSize()
//                            .clickable {
//                                for (i in track.points.indices) {
//                                    viewModel.points.add(track.points[i])
//                                }
//                                trackPicked.value = true
//                                viewModel.setToggle.value = true
//                            }
//                            .height(50.dp)
//                    ) {
//                        Text(text = track.name, textAlign = TextAlign.Center,
//                            modifier=Modifier.fillMaxSize(), fontSize = 30.sp)
//                    }
//                }
//            }
//        }
//    }

//    @Composable
//    fun TrackingButton (
//        points: SnapshotStateList<Point>,
//    ) {
//        val scope = rememberCoroutineScope()
//        viewModel.color = if (!viewModel.isToggled.value) Color(0, 153, 0) else Color(179, 0, 89)
//        viewModel.textTracking = if (!viewModel.isToggled.value) "Start Tracking" else "Stop Tracking"
//        Button(onClick = {
//
//            viewModel.isToggled.value = !viewModel.isToggled.value
//
//            if (viewModel.isToggled.value) { // Start lap tracking
//                viewModel.thread.value = scope.launch {
//                    while (viewModel.thread.value?.isActive == true) {
//                        points.forEach { point ->
//                            viewModel.next.value = point.name
//                            viewModel.getProximityFlow(point.latlon)?.first { d -> // Emit from flow until within 2 meters
//                                viewModel.distance.value = d // Update UI
//                                d < 2.0
//                            }
//                        }
//                        viewModel.laps.value += 1 // All points have been reached, +1 lap
//                    }
//                }
//            }
//            else { // Reset values and
//                viewModel.distance.value = 0.0
//                viewModel.laps.value = 0
//                viewModel.thread.value?.cancel()
//            }
//        }) {
//            Text(text = viewModel.textTracking, color = viewModel.color)
//        }
//
//        if (viewModel.thread.value?.isActive == true) {
//            Text (
//                text = "${viewModel.next.value} is ${viewModel.distance.value} m away",
//                modifier = Modifier
//                    .padding(top = 20.dp)
//            )
//            Text(text = "Laps: ${viewModel.laps.value}")
//        }
//    }
}