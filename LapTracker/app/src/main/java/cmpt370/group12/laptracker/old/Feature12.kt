package cmpt370.group12.laptracker.old
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cmpt370.group12.laptracker.view.theme.LapTrackerTheme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.*
import androidx.compose.ui.unit.sp
import cmpt370.group12.laptracker.presentation.MapState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Feature12 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LapTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                ) {

                    RealTimeUI()

                }
            }
        }
    }
}

/*
The main app composable where everything is added together
the oncreate function will call this to compose the application
 */
@Composable
fun RealTimeUI () {

    var isRunning by remember {  mutableStateOf (false)}
    val selectedViewModel = viewModel  <SelectedItemsViewModel>()
    //val stateClass = MapState()

    Column (

            modifier = Modifier.fillMaxSize()
    ){
        DropdownMenu ( selectedViewModel)

        Spacer ( modifier = Modifier.height(16.dp))

        //DisplaySelectedWidgets ( selectedViewModel)

        Spacer ( modifier = Modifier.height(16.dp))

        BottomButton(

                isRunning = isRunning,

                onStartClick = { isRunning = true},
                onStopClick = { isRunning = false},
                onResumeClick = { isRunning = true}
        )
    }
}

/*
The bottom buttoms contain start stop and resume which will all be handled differently
by the respective widget that are currently on the screen
 */
@Composable
fun BottomButton(
        isRunning: Boolean,
        onStartClick: () -> Unit,
        onStopClick: () -> Unit,
        onResumeClick: () -> Unit) {
    Row (

            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button (
                onClick = onStartClick,
                enabled = !isRunning //turn off when it's going
        ){
            Text("Start")
        }
        Button (
                onClick = onStopClick,
                enabled = isRunning //turn off when not running
        ) {
            Text("Stop")
        }
        Button(
                onClick = onResumeClick,
                enabled = !isRunning //turn off when running
        ) {
            Text("Resume")
        }
    }
}



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



//Temporary text until the widgets themselves are working
@Composable
fun TimeWidget() {
    var isRunning by remember { mutableStateOf(false) }
    var elapsedTime by remember { mutableStateOf(0L) }
    var startTime by remember { mutableStateOf(0L) }
    val scope = rememberCoroutineScope()
    Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
                text = formatElapsedTime(elapsedTime),
                style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 50.sp, // Adjust the font size as needed
                        //fontWeight = FontWeight.Bold
                )
        )
        Spacer ( modifier = Modifier.height(16.dp))
        Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TimeWidgetButton(
                    text = if (isRunning) "Stop" else "Start",
                    onClick = {
                        if (isRunning) {
                            isRunning = false
                        } else {
                            isRunning = true
                            startTime = System.currentTimeMillis() - elapsedTime
                            scope.launch {
                                while (isRunning) {
                                    elapsedTime = System.currentTimeMillis() - startTime
                                    delay(1000)
                                }
                            }
                        }
                    },
                    enabled = true
            )
            TimeWidgetButton(
                    text = "Reset",
                    onClick = {
                        isRunning = false
                        elapsedTime = 0
                    },
                    enabled = !isRunning
            )
        }
    }
}





@Composable
fun SpeedPaceWidget (speed: Float) {
    Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
        Text(
                text = "Speed: $speed",
                style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 50.sp,
                        //fontWeight = FontWeight.Bold
                )
        )
    }
}



@Composable
fun DistanceWidget () {

    val tracker = DistanceCalc()
    val local = MapState().currentLocation
    tracker.update(local)
    val distance = tracker.getDistance()
    Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text (
                text = "Distance: $distance",
                style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 50.sp,
                )
        )
    }
}



class DistanceCalc {
    //I think this only get's called once, might need a global update(), that updates all live widgets
    private var totalDistance: Float = 0f
    private var prevLocation: Location? = null
    fun update (current: Location?){
        if (prevLocation != null){
            val change = current?.let { prevLocation!!.distanceTo(it) }
            if (change != null) {
                totalDistance += change
            }
        }
        prevLocation = current
    }
    fun resetTracking(){
        totalDistance = 0f
    }
    fun getDistance(): Float {
        return totalDistance
    }
}



@Composable
fun LapsWidget () {
    //TODO
    //need to find a way to track completed laps or have some time of counter implements
    val lapsCompleted = 0
    Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text (
                text = "Laps Done: $lapsCompleted",
                style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 50.sp,
                )
        )
    }
}


@Composable
fun ProgressWidget () {
    Text("ProgressWidget")
}

//View model to manage which items are in the view, selected items
//will be shown, non selected will be deleted
class SelectedItemsViewModel : ViewModel() {
    //keep empty
    val selectedItems = mutableStateOf(emptyList<String>())
    //add the item if there's room, dont allow anything to be added
    //if there's already two selected
    fun toggleItem(item: String) {

        val selected = selectedItems.value.toMutableList()

        if ( selected.contains(item)) {

            selected.remove(item)
        } else {

            if ( selected.size < 2) {

                selected.add(item)
            }
        }
        selectedItems.value = selected
    }

}



//external composable for displaying the selected widget within the
//dropdown menu of radio boxes
@Composable
fun DisplaySelectedWidgets (selectedViewModel: cmpt370.group12.laptracker.ui.SelectedItemsViewModel) {

    val selectedItems = selectedViewModel.selectedItems.value
    val stateClass = MapState()

    //use this is you want something to happen when it's launched
    //keep empty for now
    LaunchedEffect ( selectedItems){
    }
    if ( selectedItems.contains("Time")) {
        TimeWidget()
    }

    if ( selectedItems.contains("Speed/Pace")) {
        val speed = stateClass.currentLocation?.speed
        if (speed != null) {
            SpeedPaceWidget(speed)
        }
    }

    if ( selectedItems.contains("Distance")){
        val location = stateClass.currentLocation
        //val prevLat = location?.latitude
        //val prevLong = location?.longitude
        DistanceWidget()
    }
    if ( selectedItems.contains("Laps")){
        LapsWidget()
    }
    if ( selectedItems.contains("Progress")){
        ProgressWidget()
    }
    if ( selectedItems.isEmpty()){
        //Stopwatch()
    }
}




@Composable
fun DefaultWidget() {
    Text("Default Widget")
}

//format the time into readable text 00:00:00:000
@Composable
fun formatElapsedTime ( elapsedTime: Long): String {

    val miliseconds = (elapsedTime % 1000).toInt()
    val seconds = (elapsedTime / 1000).toInt()
    val minutes = seconds / 60
    val hours = minutes / 60

    return String.format( "%02d:%02d:%02d:%03d", hours, minutes % 60, seconds % 60, miliseconds)
}



@Composable
fun TimeWidgetButton ( text: String, onClick: () -> Unit, enabled: Boolean) {

    Button(
            onClick = onClick,
            modifier = Modifier.padding(8.dp),
            enabled = enabled
    ) {
        Text(text)
    }
}


@Composable
fun Greeting13(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}
@Preview(showBackground = true)
@Composable
fun Prev() {
    LapTrackerTheme {
        RealTimeUI()
    }
}

