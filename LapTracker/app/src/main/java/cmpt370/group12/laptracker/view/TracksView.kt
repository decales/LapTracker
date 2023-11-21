package cmpt370.group12.laptracker.view

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    fun SearchBar() {
        TextField(
            value = "",
            onValueChange = {},
            shape = RoundedCornerShape(80.dp)
        )
    }


    @Composable
    fun TrackCardColumn() {
        Card(
            colors = CardDefaults.cardColors(Color(0xff1c212d)),
            modifier = Modifier.padding(20.dp)
        ) {
            if (viewModel.tracks.value.isNotEmpty()) {
                LazyColumn(
                    contentPadding = PaddingValues(top = 20.dp, start = 20.dp, end = 20.dp),
                ) {
                    viewModel.tracks.value.forEach { track ->
                        item {
                            Card(
                                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                                modifier = Modifier
                                    .padding(bottom = 25.dp)
                                    .fillMaxWidth()
                                    .clickable { viewModel.toggleTrackDetails(track) }
                            ) {
                                Row (modifier = Modifier.padding(20.dp)) {
                                    Column (
                                        modifier = Modifier
                                            .weight(0.4f)
                                    ){
                                        Text(
                                            text = track.name,
                                            fontSize = 20.sp
                                        )
                                        Text(text = track.location)
                                    }
                                    Icon(
                                        painter = painterResource(id = track.mapImage),
                                        contentDescription = track.name,
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
            else {
                Text(text = "You have no saved tracks LOL") // TODO make this screen
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TrackDetails() { // TODO finish making this
        AlertDialog(
            onDismissRequest = { viewModel.toggleTrackDetails(viewModel.selectedTrack) }
        ) {
            Card (modifier = Modifier
                .padding(0.dp)
            ) {
                Column {
                    Text(text = viewModel.selectedTrack.name)
                    Text(text = viewModel.selectedTrack.location)
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
                if (viewModel.selectedTrackRuns.isNotEmpty()) {
                    LazyColumn {
                        viewModel.selectedTrackRuns.forEach { run ->
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