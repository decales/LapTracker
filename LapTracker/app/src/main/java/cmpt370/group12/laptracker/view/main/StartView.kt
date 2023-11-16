package cmpt370.group12.laptracker.view.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cmpt370.group12.laptracker.viewmodel.main.StartViewModel
import cmpt370.group12.laptracker.viewmodel.main.StartViewModel.Point
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
        if (createRace.value) {
            trackingView()
        }
        else {
            Box (
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CreateARaceButton(createRace)
                    ChooseATrackButton()
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
    fun CreateARaceButton(createRace: MutableState<Boolean>) {
        Button(
            onClick = {
                createRace.value = true
            }
        ) {
            Text(text = "Create a Race")
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
        val points = remember { mutableStateListOf<Point>() } // Remember list of points
        var setToggle by remember { mutableStateOf(false) } // Remember set button toggle

        Box (
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box ( // Container to display location points
                    modifier = Modifier
                        .padding(bottom = 100.dp)
                ) {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(top = 10.dp)
                    ) {

                        points.forEach { point ->
                            Text(text = point.name)
                        }
                    }
                }
                ToggleSetPointsButton(points, setToggle) { setToggle = !setToggle }

                if (points.isNotEmpty() && !setToggle) {
                    TrackingButton(points)
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
        val text = if (points.isEmpty() && !setToggle) "Set points" else if (!setToggle) "Edit points" else "Done"

        Button(onClick = onClick) {
            Text(text = text)
        }
        if (setToggle) { // When button is toggled on, display another button to set (and undo) a point
            SetPointButton(points)
        }
    }


    @Composable
    fun SetPointButton (
        points: SnapshotStateList<Point>
    ) {
        val scope = CoroutineScope(Dispatchers.Main)
        var isLoading by remember { mutableStateOf(false) }
        val text = if (points.isEmpty()) "Set start" else "Set point"

        Button( // Set a new point and add it to points array
            onClick = {
                isLoading = true
                scope.launch {
                    val pointID = if (points.isEmpty()) "Start" else "L${points.size}"
                    points.add(Point(viewModel.getAverageLocation(), pointID,false))
                    isLoading = false
                }
            }
        ) {
            Text(text = text)
        }
        if (points.isNotEmpty()) {
            Button( // Remove last point entry from points array
                onClick = { points.removeLast() }
            ) {
                Text(text = "Undo")
            }
        }
        if (isLoading) {
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
        var isToggled by remember { mutableStateOf(false) }
        val color = if (!isToggled) Color(0, 153, 0) else Color(179, 0, 89)
        val text = if (!isToggled) "Start Tracking" else "Stop Tracking"
        var thread by remember { mutableStateOf<Job?>(null) }
        val scope = rememberCoroutineScope()

        // Tracking UI variables
        var distance by remember { mutableStateOf(0.0) }
        var laps by remember { mutableStateOf(0) }
        var next by remember { mutableStateOf("")}

        Button(onClick = {

            isToggled = !isToggled

            if (isToggled) { // Start lap tracking
                thread = scope.launch {
                    while (thread?.isActive == true) {
                        points.forEach { point ->
                            next = point.name
                            viewModel.getProximityFlow(point.latlon).first { d -> // Emit from flow until within 2 meters
                                distance = d // Update UI
                                d < 2.0
                            }
                        }
                        laps += 1 // All points have been reached, +1 lap
                    }
                }
            }
            else { // Reset values and
                distance = 0.0
                laps = 0
                thread?.cancel()
            }
        }) {
            Text(text = text, color = color)
        }

        if (thread?.isActive == true) {
            Text (
                text = "$next is $distance m away",
                modifier = Modifier
                    .padding(top = 20.dp)
            )
            Text(text = "Laps: $laps")
        }
    }
}