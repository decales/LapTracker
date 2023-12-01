package cmpt370.group12.laptracker.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogWindowProvider
import androidx.core.content.ContextCompat
import cmpt370.group12.laptracker.R
import cmpt370.group12.laptracker.viewmodel.TracksViewModel
import kotlinx.coroutines.delay

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
        Column (modifier = Modifier.padding(20.dp)) {
            Header()
            if (!viewModel.trackDetailsVisible) TrackListView()
            else TrackDetailsView()
            if (viewModel.deleteConfirmationVisible) DeleteConfirmation()
        }

    }


    @Composable
    fun Header() {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
        ) {
            Text(
                text = "Saved Tracks",
                fontSize = 30.sp
            )
        }
    }


    @Composable
    fun TrackListView() {
        TrackListToolBar()
        TrackList()
    }


    @Composable
    fun TrackListToolBar() {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
        ) {
            Button(onClick = { viewModel.addTrack()}) { // TODO Remove this later
                Text(text = "add")
            }
            Spacer(modifier = Modifier.weight(1.0f))
            if (viewModel.deleteModeToggled) {
                Button(
                    onClick = { viewModel.toggleDeleteConfirmation() },
                    enabled = viewModel.tracksCards.value.any { it.isSelected.value }
                ) {
                    Text(text = "Delete selected")
                }
            }
            FilledIconToggleButton(
                checked = viewModel.deleteModeToggled,
                onCheckedChange = { viewModel.deleteModeToggled = !viewModel.deleteModeToggled},
                modifier = Modifier.padding(start = 5.dp)
            ) {
                Icon(painter = painterResource(id = R.drawable.tracksview_delete),
                    contentDescription = "delete track")
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TrackList() {
        Card(
            colors = CardDefaults.cardColors(Color(ContextCompat.getColor(LocalContext.current, R.color.cardSecondary))),
            modifier = Modifier.fillMaxSize()
        ) {
            if (viewModel.tracksCards.value.isNotEmpty()) {
                LazyColumn(
                    contentPadding = PaddingValues(top = 20.dp, start = 20.dp, end = 20.dp),
                ) {
                    viewModel.tracksCards.value.forEach { trackCard ->
                        item {
                            BadgedBox(badge = {
                                if (viewModel.deleteModeToggled && trackCard.isSelected.value) { // Display badge in corner of track card to select tracks to be deleted
                                    Badge(
                                        containerColor = Color(0xFF91b5fa),
                                        contentColor = Color.White,
                                        modifier = Modifier
                                            .graphicsLayer {
                                                translationY = 15.0F
                                                translationX = -40.0F
                                            }
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
                Box(
                    contentAlignment = Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = "It's empty in here :(")
                }
            }
        }
    }


    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun TrackDetailsView() {// TODO give this an enter and exit animation
        Card(
            colors = CardDefaults.cardColors(Color(ContextCompat.getColor(LocalContext.current, R.color.cardSecondary))),
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(top = 10.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
            ) {
                TrackDetailsToolBar()
                Text(text = viewModel.currentTrackDetails.name, fontSize = 28.sp)
                TrackDetailsTabBar()

                val pagerState = rememberPagerState { 3 } // TODO sync tab bar with pagerState
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                ) {
                    Card(modifier = Modifier.fillMaxSize()) {
                        when (it) {
                            0 -> TrackDetailsOverview()
                            1 -> TrackDetailsRuns()
                            2 -> TrackDetailsNotes()
                            else -> Text(text = "?")
                        }
                    }
                }
            }
        }
        viewModel.getAchievementState("Checked First Track")
        viewModel.achievements.ShowAchievement("Checked First Track", viewModel.updateAchievement, viewModel.achieved)
        LaunchedEffect(Unit) {
            viewModel.updateAchievement = true
            delay(2000)
            viewModel.updateAchievement = false
        }
    }


    @Composable
    fun TrackDetailsToolBar() {
        Row (
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
        ) {
            OutlinedIconButton( // Back button
                onClick = { viewModel.trackDetailsVisible = !viewModel.trackDetailsVisible }
            ) {
                Icon(painter = painterResource(id = R.drawable.tracksview_back), contentDescription = "tracksview_detailsback")
            }
            Spacer(modifier = Modifier.weight(1.0f))
            FilledIconButton( // Edit track button
                onClick = { /* TODO edit track function */ },
            ) {
                Icon(painter = painterResource(id = R.drawable.tracksview_edit), contentDescription = "tracksview_detailsback")
            }
            FilledIconButton( // Delete track button
                onClick = { viewModel.toggleDeleteConfirmation() },
                modifier = Modifier.padding(start = 5.dp)
            ) {
                Icon(painter = painterResource(id = R.drawable.tracksview_delete), contentDescription = "tracksview_detailsback")
            }
        }
    }


    @Composable
    fun TrackDetailsTabBar() {
        TabRow(
            selectedTabIndex = 0,
            containerColor = Color.Transparent,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 20.dp)
        ) {
            listOf("Overview", "Run history", "Notes").forEachIndexed { index, item ->
                Tab(
                    selected = false,
                    onClick = { /* TODO */ }
                ) {
                    Text(
                        text = item,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                }
            }
        }
    }



    @Composable
    fun TrackDetailsOverview(/*lapDataList: List<LapData> */) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = CenterHorizontally
        ) {
//            Text(text = "Average Speed: %.2f km/h".format(GetAverageSpeed(0)))
//            Text(text = "Total Distance Travelled: %.2f km".format(GetTotalDistance(0)))
//            Text(text = "Total Time Spent on Track: %02d:%02d".format(GetTotalTimeSpentOnTrack(0)))
        }
    }

    @Composable
    fun TrackDetailsRuns() { // TODO make this
        Box(
            contentAlignment = Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                Text(text = "Run History")
                if (viewModel.currentTrackDetailsRuns.isNotEmpty()) {
                    LazyColumn {
                        viewModel.currentTrackDetailsRuns.forEach { run ->
                            item {
                                Text(text = "Date: Start time: ${run.startTime} End time: ${run.endTime}")
                            }
                        }
                    }
                }
                else Text(text = "You have not completed any runs on this track!")
            }
        }
    }


    @Composable
    fun TrackDetailsNotes() { // TODO make this
        Box(
            contentAlignment = Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Overview")
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DeleteConfirmation() {
        // Change message and delete function depending on if deletion is triggered from track details or track list
        val mode: Pair<String,()-> Unit> = when(viewModel.trackDetailsVisible) {
            true -> Pair("Are you sure you want to permanently delete this track?") { viewModel.deleteCurrentTrack() }
            false -> Pair("Are you sure want to permanently delete the ${viewModel.tracksCards.value.filter { it.isSelected.value }.size} selected track(s)?") { viewModel.deleteSelectedTracks() }
        }
        AlertDialog(onDismissRequest = { viewModel.toggleDeleteConfirmation() }) {
            (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0.8f)
            Card {
                Column(
                    horizontalAlignment = CenterHorizontally,
                    modifier = Modifier.padding(20.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.tracksview_delete),
                        contentDescription = "tracksview_delete",
                        modifier = Modifier.size(32.dp)
                    )
                    Text(
                        text = "Confirm deletion",
                        fontSize = 26.sp,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                    Text(
                        text = mode.first, // mode string
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Row (
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier
                            .padding(top = 15.dp, start = 40.dp, end = 40.dp)
                            .fillMaxWidth()
                    ){
                        OutlinedButton(onClick = { viewModel.toggleDeleteConfirmation() }) {
                            Text(text = "Cancel", color = Color(0xFFce3e5a))
                        }
                        OutlinedButton(onClick = mode.second // mode function
                        ) {
                            Text(text = "Confirm")
                        }
                    }
                }
            }
        }
    }
}