package cmpt370.group12.laptracker.view

import android.location.Geocoder
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogWindowProvider
import cmpt370.group12.laptracker.R
import cmpt370.group12.laptracker.viewmodel.StartViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartView(
    private val viewModel: StartViewModel
) {
    /* View:
        - Represent the entire start view
        - Made up of class defined composable functions
        - All data is stored retrieved from class view model */
    @Composable
    fun View() {
        //Immediately check location services
        if (!viewModel.locationClient.servicesEnabled() || !viewModel.locationClient.hasLocationPermissions()) viewModel.viewState = StartViewModel.ViewState.NoServices
        Column {
            Header()
            Box {
                MapView()
                when(viewModel.viewState) {
                    StartViewModel.ViewState.ChooseMode -> ChooseModeView()
                    StartViewModel.ViewState.NewTrack -> NewTrackView()
                    StartViewModel.ViewState.LoadTrack -> LoadTrackView()
                    StartViewModel.ViewState.SaveTrack -> SaveTrackView()
                    StartViewModel.ViewState.InRun -> InRunView()
                    StartViewModel.ViewState.PostRun -> PostRunView()
                    StartViewModel.ViewState.NoServices -> NoServicesView()
                }
            }
        }
    }


    @Composable
    fun Header() {
        val text: String = when(viewModel.viewState) {
            StartViewModel.ViewState.ChooseMode -> "Select track mode"
            StartViewModel.ViewState.NewTrack -> "Create a track"
            StartViewModel.ViewState.LoadTrack -> "Load a track"
            StartViewModel.ViewState.SaveTrack -> "New track"
            StartViewModel.ViewState.InRun -> "Start tracking"
            StartViewModel.ViewState.PostRun -> "Post tracking"
            StartViewModel.ViewState.NoServices -> "Select track mode"
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp)
        )
        {
            Text(
                text = text,
                fontSize = 30.sp
            )
        }
    }


    @Composable
    fun ChooseModeView() {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 200.dp, bottom = 200.dp, start = 30.dp, end = 30.dp)
        ) {
            Column {
                listOf(
                    Triple("New Track", R.drawable.ic_launcher_foreground) { viewModel.launchNewTrack() },
                    Triple("Load Track", R.drawable.ic_launcher_foreground) { viewModel.viewState = StartViewModel.ViewState.LoadTrack }
                ).forEach { item ->
                    Card(modifier = Modifier
                        .fillMaxSize()
                        .weight(0.5F)
                        .padding(10.dp)
                        .shadow(20.dp)
                        .clickable(onClick = item.third)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp)
                        ) {
                            Text(text = item.first, fontSize = 26.sp)
                            Icon(painter = painterResource(id = item.second), contentDescription = "")
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun NewTrackView() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        ) {
            Button(
                onClick = { viewModel.launchChooseMode() },
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Text(text = "Quit")
            }
            Button(
                enabled = viewModel.mapPoints.size >= 3,
                onClick = { viewModel.viewState = StartViewModel.ViewState.SaveTrack },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Text(text = "Done")
            }
            Button(
                modifier = Modifier.align(Alignment.BottomCenter),
                onClick = {
                viewModel.scope.launch {
                    val location = viewModel.locationClient.getAverageLocation(3)
                    viewModel.mapPoints.add(LatLng(location.latitude, location.longitude))
                }
            }) {
                Text(text = "Set on current location")
            }
            Button(
                enabled = viewModel.mapPoints.isNotEmpty(),
                onClick = { viewModel.mapPoints.removeLast() },
                modifier = Modifier.align(Alignment.BottomStart)
            ) {
                Text(text = "Undo")
            }
            viewModel.getAchievementState("Created First Track")
            viewModel.achievements.ShowAchievement("Created First Track", viewModel.updateAchievements, viewModel.achieved)
            LaunchedEffect(Unit) {
                viewModel.updateAchievements = true
                delay(2000)
                viewModel.updateAchievements = false
            }
        }
    }


    @Composable
    fun LoadTrackView() {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 200.dp, bottom = 200.dp, start = 50.dp, end = 50.dp)
        ) {
            Text(text = "load")
        }
        viewModel.getAchievementState("Load First Track")
        viewModel.achievements.ShowAchievement("Load First Track", viewModel.updateAchievements, viewModel.achieved)
        LaunchedEffect(Unit) {
            viewModel.updateAchievements = true
            delay(2000)
            viewModel.updateAchievements = false
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SaveTrackView() {
        var isSaved by remember { mutableStateOf(false) }
        Box(contentAlignment = Alignment.Center,) {
            AlertDialog(onDismissRequest = { viewModel.viewState = StartViewModel.ViewState.NewTrack },) {
                (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0.8f)
                Card(modifier = Modifier.padding(20.dp)) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = "",
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = "Save new track",
                            fontSize = 26.sp,
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                        if (!isSaved) {
                            Text(
                                text = "Would you like to add this track to your saved tracks?",
                                fontSize = 14.sp,
                                modifier = Modifier.padding(bottom = 20.dp)
                            )
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                OutlinedButton(
                                    onClick = { isSaved = true },
                                    modifier = Modifier.padding(bottom = 5.dp)
                                ) {
                                    Text(text = "Save track", color = Color(0xFF53c662))
                                }
                                OutlinedButton(onClick = { viewModel.launchInRun() }) {
                                    Text(text = "Continue without saving")
                                }
                            }
                        }
                        else {
                            var nameInput by remember { mutableStateOf("") }
                            val geoCoder = Geocoder(LocalView.current.context)
                            Text(
                                text = "Enter a track name",
                                modifier = Modifier.padding(bottom = 10.dp)
                            )
                            TextField(
                                value = nameInput,
                                onValueChange = { nameInput = it },
                                modifier = Modifier.padding(bottom = 20.dp)
                            )
                            OutlinedButton(
                                onClick = {
                                    viewModel.saveTrack(nameInput, geoCoder)
                                    viewModel.launchInRun()
                                      },
                                modifier = Modifier.padding(bottom = 5.dp)
                            ) {
                                Text(text = "Save and continue", color = Color(0xFF53c662))
                            }
                            OutlinedButton(onClick = { isSaved = false }) {
                                Text(text = "Cancel", color = Color(0xFFce3e5a))
                            }
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun InRunView() {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Button(
                onClick = { viewModel.launchChooseMode() },
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Text(text = "Quit")
            }
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                if (viewModel.statsBarToggled) StatisticsBar()

                else {
                    Button(onClick = {  }) {
                        Text(text = "Start tracking")
                    }
                }
            }
        }
    }


    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun StatisticsBar() {
        Card {
            val pagerState = rememberPagerState { 3 }
            HorizontalPager(state = pagerState) {
                when(pagerState.currentPage) {
                    0 -> {

                    }
                    1 -> {

                    }
                    2 -> {

                    }
                }
            }
        }
//            Text(text = "Distance from target: ")
//            Text(text = "Laps: ${viewModel.lapsCompleted}/${viewModel.lapCount}")
//            Text(text = "Current speed: ${viewModel.currentLocation?.speed}")
//            Text(text = "Distance from target: " +
//                    "${LatLng(
//                        (viewModel.currentLocation?.latitude?.minus(viewModel.nextPoint.latitude)!!),
//                        (viewModel.currentLocation?.longitude?.minus(viewModel.nextPoint.longitude)!!)
//                    )}")
    }


    @Composable
    fun PostRunView() {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 200.dp, bottom = 200.dp, start = 50.dp, end = 50.dp)
        ) {
            Text(text = "post run")
        }
    }


    @Composable
    fun NoServicesView() {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 50.dp, end = 50.dp)
        ) {
            Card {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(20.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.startview_noservices),
                        contentDescription = "",
                        modifier = Modifier
                            .size(48.dp)
                            .padding(bottom = 20.dp)
                    )
                    if (!viewModel.locationClient.servicesEnabled()) {
                        Text(
                            text = "Location services not enabled",
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 20.dp))
                        Text(
                            text = "Please enable your device's cellular and/or GPS services to continue.",
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                    }
                    else {
                        Text(
                            text = "Location permissions not granted",
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 20.dp))
                        Text(text = "Please allow access to your device's precise location to continue.", modifier = Modifier.padding(bottom = 20.dp))
                        Button(onClick = { viewModel.locationClient.getLocationPermission() }) {
                            Text(text = "Permissions")
                        }
                    }
                    Button(onClick = { viewModel.viewState = StartViewModel.ViewState.ChooseMode }) {
                        Text(text = "Refresh")
                    }
                }
            }
        }
    }


    @Composable
    fun MapView() {
        val cameraState by remember { mutableStateOf(CameraPositionState()) }

        Card(modifier = Modifier.padding(20.dp)) {
            GoogleMap(
                properties = viewModel.mapProperties,
                uiSettings = viewModel.mapSettings,
                cameraPositionState = cameraState,
                onMapClick = {viewModel.mapPoints.add(it)},
                modifier = Modifier
                    .fillMaxSize()
                    .blur(if (!viewModel.mapIsEnabled) 5.dp else 0.dp),

            ) {
                if (viewModel.mapIsEnabled) {
                    Marker(
                        title = "Current location",
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED),
                        state = MarkerState(if (viewModel.currentLocation != null) LatLng(viewModel.currentLocation!!.latitude, viewModel.currentLocation!!.longitude) else LatLng(0.0,0.0)))
                    viewModel.mapPoints.forEachIndexed { index, point ->
                        Marker(
                            state = MarkerState(LatLng(point.latitude, point.longitude)),
                            title = "(${point.latitude}, ${point.longitude})",
                            snippet = "Long click to delete",
                            onClick = { it.showInfoWindow(); true },
                            icon =
                            if (index == 0) BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                            else BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                        )
                    }
                }
                Polyline(points = viewModel.mapPoints.toList(), width = 4F)
                if (viewModel.mapPoints.size >= 2) Polyline(points = listOf(viewModel.mapPoints.toList().first(), viewModel.mapPoints.toList().last()), width = 4F)

                LaunchedEffect(viewModel.mapIsEnabled) {
                    if (!viewModel.mapIsEnabled) viewModel.panMapCamera(cameraState)
                    else viewModel.panMapCameraToCurrentLocation(cameraState)
                }
            }
        }
    }

    @Composable
    fun TrackListView(trackPicked: MutableState<Boolean>) {
        LaunchedEffect(Unit) {
            viewModel.fetchTracks()
        }
        Box (
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(width = 250.dp, height = 350.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                viewModel.tracksCards.value.forEach { track ->
                    Card(
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .fillMaxSize()
                            .clickable {
                                //for (i in track.points.indices) {
                                //    viewModel.points.add(track.points[i])
                                //}
                                trackPicked.value = true
                                viewModel.setToggle.value = true
                            }
                            .height(50.dp)
                    ) {
                        Text(text = track.track.name, textAlign = TextAlign.Center,
                            modifier=Modifier.fillMaxSize(), fontSize = 30.sp)
                    }
                }
            }
        }
    }


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