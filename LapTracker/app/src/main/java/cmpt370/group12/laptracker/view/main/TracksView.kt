package cmpt370.group12.laptracker.view.main

import android.graphics.drawable.shapes.Shape
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cmpt370.group12.laptracker.viewmodel.main.TracksViewModel
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction

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
            TrackDetails()
            TrackCardColumn()
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
                text = "Tracks",
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
            LazyColumn(
                contentPadding = PaddingValues(top = 20.dp, start = 20.dp, end = 20.dp),
            ) {
                viewModel.trackCards.forEach { card ->
                    item {
                        Card(
                            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                            modifier = Modifier
                                .padding(bottom = 25.dp)
                                .fillMaxWidth()
                                .clickable {
                                /* TODO launch /student/jpm715/StudioProjects/cmpt-370track details view */ }
                        ) {
                            Row (modifier = Modifier.padding(20.dp)) {
                                Column (
                                    modifier = Modifier
                                        .weight(0.4f)
                                ){
                                    Text(
                                        text = card.name,
                                        fontSize = 20.sp
                                    )
                                    Text(text = card.location)
                                }
                                Icon(
                                    painter = painterResource(id = card.mapSnippet),
                                    contentDescription = card.name,
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


    @Composable
    fun TrackDetails() {
        Card(
            colors = CardDefaults.cardColors(Color(0xff1c212d)),
            modifier = Modifier.padding(40.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Track Name...", color = Color(255, 255, 255))

            var comment by remember { mutableStateOf("Type here...") }
            TextField(
                value = comment,
                onValueChange = { newComment -> comment = newComment },
                label = { Text(text = "Type Comments Here") },
                maxLines = 3,
                keyboardOptions = KeyboardOptions(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
                keyboardActions = KeyboardActions(onGo = {
                    Log.d(
                        "ImeAction",
                        "Comment Saved" /* TODO save comment to database */
                    )
                })
            )

            Button(onClick = {}) {
                Text(text = "Save Comment") /* TODO button click = save comment to database */
            }

            LazyColumn(
                contentPadding = PaddingValues(top = 20.dp, start = 20.dp, end = 20.dp),
            ) {
                viewModel.trackCards.forEach { card ->
                    item {
                        Card(
                            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                            modifier = Modifier
                                .padding(bottom = 25.dp)
                                .fillMaxWidth()
                                .clickable {
                                    /* TODO launch /student/jpm715/StudioProjects/cmpt-370track details view */
                                }
                        ) {

                        }
                    }
                }
            }
        }
    }
}