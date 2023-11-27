package cmpt370.group12.laptracker.view.composables

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cmpt370.group12.laptracker.R
import cmpt370.group12.laptracker.model.domain.model.Track
import cmpt370.group12.laptracker.viewmodel.GlobalViewModel

@Composable
fun CreateTrackCard(viewModel: GlobalViewModel) {
    var nametext by remember { mutableStateOf("") }
    var desctext by remember { mutableStateOf("") }
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 25.dp
        ),
        //colors = CardDefaults.cardColors(containerColor = Color.LightGray),
        modifier = Modifier
            .size(width = 250.dp, height = 300.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(horizontalArrangement = Arrangement.Center){
                Text(modifier = Modifier.
                padding(10.dp),
                    text = "Create a New Track"
                )
            }

            Row {

                //var text : String = ""
                Column(modifier = Modifier
                    .width(190.dp)
                ) {
                    OutlinedTextField(

                        value = nametext,
                        onValueChange = { nametext = it },
                        label = { Text("Name") },
                        singleLine = true

                    )
                    OutlinedTextField(

                        value = desctext,
                        onValueChange = {if (it.length <= 60) desctext = it },
                        label = { Text("Description") },
                        maxLines = 3,
                        minLines = 3

                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Row {
                Button(
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(5.dp),
                    onClick = {
                        viewModel.Create_Track(Track(name = nametext, location = desctext, mapImage = R.drawable.ic_launcher_foreground))
                        viewModel.Set_isStartCardVisible(false)
                        viewModel.Set_isCreateTrackVisible(false)
                        if (viewModel.appstate.value.mainNavController?.currentDestination?.route !="Start") {
                            viewModel.appstate.value.mainNavController?.navigate("Start")
                        }
                        }

                ) {
                    Text(text = "Create")
                }
                Spacer(modifier = Modifier.weight(0.05f))
                Button(
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(5.dp),
                    onClick = {
                        //viewModel.backend.Set_isStartCardVisible(true)
                        viewModel.Set_isCreateTrackVisible(false)
                    }
                ) {
                    Text(text = "Cancel")
                }
            }



        }

    }
}

@Composable
fun StartupCard(viewModel: GlobalViewModel){

    Card(
        border = BorderStroke(4.dp, Color.Transparent),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier
            .size(width = 180.dp, height = 150.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    //viewModel.backend.Set_isStartCardVisible(false)
                    viewModel.Set_isCreateTrackVisible(true)
                }
            ) {
                Text(text = "Create a Track")
            }
            Button(
                onClick = {
                    viewModel.appstate.value.mainNavController?.navigate("Tracks")
                }
            ) {
                Text(text = "Load a Track")
            }

        }

    }

}