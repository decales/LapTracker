package cmpt370.group12.laptracker.view

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cmpt370.group12.laptracker.R
import cmpt370.group12.laptracker.viewmodel.TracksViewModel

class TracksView(
    private val viewModel: TracksViewModel
) {
    /* View:
        - Represent the entire tracks view
        - Made up of class defined composable functions
        - All data is stored retrieved from class view model */
    @Composable
    fun View() {
        // TODO build view from class defined composable functions.
        // TODO initialize necessary view data in viewmodel/main/TracksViewModel.kt. Data is accessed through constructor var 'viewModel'
        Column {
            Header()
            ToolBar()
            TrackCardColumn()
        }
        if (viewModel.trackDetailsVisible) TrackDetails()
    }


    @Composable
    fun Header() {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp)
        )
        {
            Text(
                text = "Saved Tracks",
                fontSize = 30.sp
            )
        }
    }


    @Composable
    fun ToolBar() {
//        TextField(
//            value = "",
//            onValueChange = {},
//            shape = RoundedCornerShape(80.dp)
//        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {viewModel.addTrack()}) {
                Text(text = "test add")
            }
            if (viewModel.deleteModeToggled) {
                Button(onClick = { viewModel.deleteSelectedTracks() }) {
                    Text(text = "Delete selected")
                }
            }
            
            FilledIconToggleButton(checked = viewModel.deleteModeToggled, onCheckedChange = { viewModel.toggleDeleteMode()}) {
                Icon(painter = painterResource(id = R.drawable.tracksview_delete),
                    contentDescription = "delete track")
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TrackCardColumn() {
        Card(
            colors = CardDefaults.cardColors(Color(0xff1c212d)),
            modifier = Modifier.padding(20.dp)
        ) {
            if (viewModel.tracksCards.value.isNotEmpty()) {
                LazyColumn(
                    contentPadding = PaddingValues(top = 20.dp, start = 20.dp, end = 20.dp),
                ) {
                    viewModel.tracksCards.value.forEach { trackCard ->
                        item {
                            BadgedBox(badge = {
                                if (viewModel.deleteModeToggled) { // Display badge in corner of track card to select tracks to be deleted
                                    Badge(
                                        modifier = Modifier
                                            .graphicsLayer {
                                                translationY = 15.0F
                                                translationX = -40.0F
                                            },
                                        containerColor = if (trackCard.isSelected.value) Color.LightGray else Color.Transparent,
                                        contentColor = if (trackCard.isSelected.value) Color.Black else Color.Transparent
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.tracksview_selected),
                                            contentDescription = "selected badge",
                                            modifier = Modifier.height(32.dp)
                                        )

                                    }
                                }
                            }
                            ) {
                                Card(
                                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                                    modifier = Modifier
                                        .padding(bottom = 25.dp)
                                        .fillMaxWidth()
                                        .clickable {
                                            if (viewModel.deleteModeToggled) viewModel.toggleSelectTrack(trackCard)
                                            else viewModel.toggleTrackDetails(trackCard.track)
                                        }
                                ) {
                                    Row (modifier = Modifier.padding(20.dp)) {
                                        Column (
                                            modifier = Modifier
                                                .weight(0.4f)
                                        ){
                                            Text(
                                                text = trackCard.track.name,
                                                fontSize = 20.sp
                                            )
                                            Text(text = trackCard.track.location)
                                        }
                                        Icon(
                                            painter = painterResource(id = trackCard.track.mapImage),
                                            contentDescription = trackCard.track.name,
                                            modifier = Modifier
                                                .weight(0.6f)
                                                .border(
                                                    width = 1.dp,
                                                    color = Color.White,
                                                    shape = RoundedCornerShape(10.dp)
                                                )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else {
                Text(text = "You have no saved tracks LOL") // TODO make this screen
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TrackDetails() { // TODO finish making this
        AlertDialog(
            onDismissRequest = { viewModel.toggleTrackDetails(viewModel.currentTrackDetails) }
        ) {
            Card (modifier = Modifier
                .padding(0.dp)
            ) {
                Column {
                    Text(text = viewModel.currentTrackDetails.name)
                    Text(text = viewModel.currentTrackDetails.location)
                    TrackDetailsRuns()
                }
            }
        }
    }


    @Composable
    fun TrackDetailsRuns() { // TODO finish making this
        Box {
            Column {
                Text(text = "Run History")
            }
            Card {
                if (viewModel.currentTrackDetailsRuns.isNotEmpty()) {
                    LazyColumn {
                        viewModel.currentTrackDetailsRuns.forEach { run ->
                            item {
                                Text(text = "Date: Start time: ${run.startTime} End time: ${run.endTime}")
                            }
                        }
                    }
                }
                else {
                    Text(text = "You have not completed any runs on this track!")
                }
            }
        }
    }
}