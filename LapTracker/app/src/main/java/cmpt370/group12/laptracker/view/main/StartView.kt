package cmpt370.group12.laptracker.view.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cmpt370.group12.laptracker.viewmodel.main.StartViewModel
import cmpt370.group12.laptracker.viewmodel.main.StartViewModel.Point
import kotlinx.coroutines.flow.first
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
        // TODO build view from class defined composable functions.
        // TODO initialize necessary view data in viewmodel/main/StartViewModel.kt. Data is accessed through constructor var 'viewModel'
        val createRace = remember { mutableStateOf(false)}
        Header()
        Box (
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (createRace.value) {
                trackingView()
            } else {
                Card(
                    modifier = Modifier
                        .padding(5.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CreateATrackButton(createRace)
                        ChooseATrackButton()
                    }
                }
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
    fun CreateATrackButton(createRace: MutableState<Boolean>) {
        Button(
            onClick = {
                createRace.value = true
            }
        ) {
            Text(text = "Create a Track")
        }
    }

    @Composable
    fun ChooseATrackButton() {
        Button( // Set a new point and add it to points array
            onClick = {
            }
        ) {
            Text(text = "Choose a Track")
        }
    }

    @Composable
    fun trackingView() {

        Box (
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box( // Container to display location points
                        modifier = Modifier
                            .padding(bottom = 100.dp),
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(top = 10.dp)
                        ) {

                            viewModel.points.forEach { point ->
                                Text(text = point.name)
                            }
                        }
                    }
                    ToggleSetPointsButton(viewModel.points, viewModel.setToggle.value) { viewModel.setToggle.value = !viewModel.setToggle.value }
                    if (viewModel.points.isNotEmpty() && !viewModel.setToggle.value) {
                            TrackingButton(viewModel.points)
                    }
                }
            }
        }
    }


    @Composable
    fun ToggleSetPointsButton(
        points: SnapshotStateList<Point>,
        setToggle: Boolean,
        onClick: () -> Unit
    ) {
        viewModel.textToggleSetPoints = if (points.isEmpty() && !setToggle) "Set points" else if (!setToggle) "Edit points" else "Done"
        Button(onClick = onClick) {
            Text(text = viewModel.textToggleSetPoints)
        }
        if (setToggle) { // When button is toggled on, display another button to set (and undo) a point
            SetPointButton(points)
        }
    }


    @Composable
    fun SetPointButton (
        points: SnapshotStateList<Point>
    ) {
        viewModel.textSetPoints = if (points.isEmpty()) "Set start" else "Set point"
        Button( // Set a new point and add it to points array
            onClick = {
                viewModel.isLoading.value = true
                viewModel.scope.launch {
                    val pointID = if (points.isEmpty()) "Start" else "L${points.size}"
                    points.add(Point(viewModel.getAverageLocation(), pointID,false))
                    viewModel.isLoading.value = false
                }
            }
        ) {
            Text(text = viewModel.textSetPoints)
        }
        if (points.isNotEmpty()) {
            Button( // Remove last point entry from points array
                onClick = { points.removeLast() }
            ) {
                Text(text = "Undo")
            }
        }
        if (viewModel.isLoading.value) {
            Text (
                text = "Setting point...",
                modifier = Modifier
                    .padding(top = 20.dp))
        }
    }

    @Composable
    fun TrackingButton (
        points: SnapshotStateList<Point>,
    ) {
        val scope = rememberCoroutineScope()
        viewModel.color = if (!viewModel.isToggled.value) Color(0, 153, 0) else Color(179, 0, 89)
        viewModel.textTracking = if (!viewModel.isToggled.value) "Start Tracking" else "Stop Tracking"
        Button(onClick = {

            viewModel.isToggled.value = !viewModel.isToggled.value

            if (viewModel.isToggled.value) { // Start lap tracking
                viewModel.thread.value = scope.launch {
                    while (viewModel.thread.value?.isActive == true) {
                        points.forEach { point ->
                            viewModel.next.value = point.name
                            viewModel.getProximityFlow(point.latlon).first { d -> // Emit from flow until within 2 meters
                                viewModel.distance.value = d // Update UI
                                d < 2.0
                            }
                        }
                        viewModel.laps.value += 1 // All points have been reached, +1 lap
                    }
                }
            }
            else { // Reset values and
                viewModel.distance.value = 0.0
                viewModel.laps.value = 0
                viewModel.thread.value?.cancel()
            }
        }) {
            Text(text = viewModel.textTracking, color = viewModel.color)
        }

        if (viewModel.thread.value?.isActive == true) {
            Text (
                text = "${viewModel.next.value} is ${viewModel.distance.value} m away",
                modifier = Modifier
                    .padding(top = 20.dp)
            )
            Text(text = "Laps: ${viewModel.laps.value}")
        }
    }
}