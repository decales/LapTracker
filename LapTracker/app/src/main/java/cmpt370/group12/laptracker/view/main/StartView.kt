package cmpt370.group12.laptracker.view.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cmpt370.group12.laptracker.viewmodel.main.StartViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
        //Row()
        //{
            Header()
        //}
        //Row()
        //{
                mainScreen()
        //}
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
    fun mainScreen() {
        val points = remember { mutableStateListOf<Pair<Double,Double>>() } // Remember list of points
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (points.isNotEmpty()) {
                        Text(text = "Would you like to set a segment?")
                    }
                }
            }
        }
        Box (
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box( // Container to display location points
                    modifier = Modifier
                        .padding(bottom = 100.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        points.forEach { point ->
                            Text(text = point.toString())
                        }
                    }
                }
                if (points.isNotEmpty()) {
                    getAnotherSegmentButton(points)
                }
                else {
                    setStartLocationButton(points)
                }
            }
        }
    }

    @Composable
    fun setStartLocationButton(points: SnapshotStateList<Pair<Double, Double>>) {
        val scope = CoroutineScope(Dispatchers.Main)
        var isLoading by remember { mutableStateOf(false) }
        isLoading = true
        Button( // Set a new point and add it to points array
            onClick = {
                isLoading = true
                scope.launch {
                    points.add(viewModel.getAverageLocation())
                    isLoading = false
                }
            }
        ) {
            Text("Set Start Location")
        }
    }

    @Composable
    fun getAnotherSegmentButton(points: SnapshotStateList<Pair<Double, Double>>) {
        val scope = CoroutineScope(Dispatchers.Main)
        var isLoading by remember { mutableStateOf(false) }
        isLoading = true
        Button( // Set a new point and add it to points array
            onClick = {
                isLoading = true
                scope.launch {
                    points.add(viewModel.getAverageLocation())
                    isLoading = false
                }
            }
        ) {
            Text("Set a Segment")
        }

        Button(
            onClick = {

            }
        ) {
            Text("Finished")
        }
    }
}