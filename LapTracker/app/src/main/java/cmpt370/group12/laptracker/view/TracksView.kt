package cmpt370.group12.laptracker.view.main

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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
            Row(){
                Text(
                    text = "Saved Tracks",
                    fontSize = 30.sp
                )
                Button(onClick = { viewModel.backend.Set_isCreateTrackVisible(true)}) {
                    
                }
            }

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
                viewModel.backend.appstate.value.tracks.forEach { card ->
                    item {
                        Card(
                            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                            modifier = Modifier
                                .padding(bottom = 25.dp)
                                .fillMaxWidth()
                                .clickable { /* TODO launch track details view */ }
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
                                    Text(text = card.description)
                                }
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
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
                if (viewModel.backend.appstate.value.isCreateTrackCardVisible){

                }
            }
        }
    }


    @Composable
    fun TrackDetails() {

    }
}