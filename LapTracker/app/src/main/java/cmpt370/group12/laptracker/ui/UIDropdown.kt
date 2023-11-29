package cmpt370.group12.laptracker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cmpt370.group12.laptracker.presentation.MapState

class UIDropdown {
    @Composable
    fun DropdownMenu(selectedItemsViewModel: SelectedItemsViewModel) {
        //list of possible options
        val options = listOf ( "Time", "Speed/Pace", "Distance", "Laps", "Progress")
        //set the default to have the first two selected
        val selectedItems = remember { mutableStateListOf<String>() }
        //to keep track of wether the button is open or nah
        var dropOpen by remember { mutableStateOf(false) }

        Column(
                modifier = Modifier
                        //.fillMaxSize()
                        .fillMaxWidth()
                        .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End
        ) {
            /*
            Text(
                    //temp for now, used to show which items are selected
                    text = "Selected Options: ${selectedItems.joinToString(", ")}",
                    modifier = Modifier.padding(16.dp)
            )
            */

            Button(
                    onClick = { dropOpen = true },
                    modifier = Modifier.padding(16.dp)
            ) {
                Text("Selected: ")
            }
            if (dropOpen) {
                AlertDialog(
                        onDismissRequest = { dropOpen = false },
                        //the title of it when it opens
                        title = { Text(text = "Items") },
                        text = {
                            Column {
                                options.forEach { option ->
                                    val isChecked = selectedItems.contains(option)
                                    Row(
                                            //center them within the dropdown menu
                                            verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Checkbox(
                                                checked = isChecked,
                                                onCheckedChange = { isChecked ->
                                                    if (isChecked) {
                                                        //keep a maximum of two widgets selected at a time
                                                        if ( selectedItems.size < 2) {
                                                            selectedItems.add(option)
                                                            //toggle the view on SELECT
                                                            selectedItemsViewModel.toggleItem(option)

                                                        }
                                                    } else {
                                                        selectedItems.remove(option)
                                                        //toggle the view on DESELECT
                                                        selectedItemsViewModel.toggleItem(option)
                                                    }
                                                }
                                        )

                                        Spacer ( modifier = Modifier.width(8.dp))
                                        Text ( text = option)
                                    }
                                }
                            }
                        },
                        confirmButton = {
                            //leave this blank don't need a confirm button
                            //user can just click/tap away to close the popup
                        }

                )
            }
        }
    }
    //external composable for displaying the selected widget within the
    //dropdown menu of radio boxes
    @Composable
    fun DisplaySelectedWidgets ( selectedViewModel: SelectedItemsViewModel) {

        val selectedItems = selectedViewModel.selectedItems.value
        val stateClass = MapState()

        //use this is you want something to happen when it's launched
        //keep empty for now
        LaunchedEffect ( selectedItems){
        }
        if ( selectedItems.contains("Time")) {
            TimerWidget()
        }

        if ( selectedItems.contains("Speed/Pace")) {
            /*
            val speed = stateClass.currentLocation?.speed
            if (speed != null) {
                SpeedWidget()
            }
             */
            //should be able to just call this now because it accesses the data independently
            SpeedWidget()
        }

        if ( selectedItems.contains("Distance")){
            val location = stateClass.currentLocation
            //val prevLat = location?.latitude
            //val prevLong = location?.longitude
            DistanceWidget()
        }
        if ( selectedItems.contains("Laps")){
            LapWidget()
        }
        /*
        if ( selectedItems.contains("Progress")){
            ProgressWidget()
        }
         */
        if ( selectedItems.isEmpty()){
            //Stopwatch()
        }
    }

}