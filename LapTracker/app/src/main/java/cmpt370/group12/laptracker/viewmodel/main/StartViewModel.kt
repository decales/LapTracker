package cmpt370.group12.laptracker.viewmodel.main

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import cmpt370.group12.laptracker.R
import cmpt370.group12.laptracker.model.LocationClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

class StartViewModel(locationClient: LocationClient) : ViewModel() {
    // TODO add all data and states values required for SettingsView composable functions
    // TODO (if applicable) retrieve data from database (model)
    var createRace = mutableStateOf(false)
    var pickTrack = mutableStateOf(false)
    var location = locationClient
    val points = mutableStateListOf<Point>()
    var setToggle = mutableStateOf(false)
    var textToggleSetPoints = "Set points"
    val scope = CoroutineScope(Dispatchers.Main)
    var textSetPoints = "Set start"
    var isLoading = mutableStateOf(false)
    var isToggled = mutableStateOf(false)
    var color = Color(0, 153, 0)
    var textTracking = "Start Tracking"
    var thread = mutableStateOf<Job?>(null)

    // Tracking UI variables
    var distance = mutableStateOf(0.0)
    var laps = mutableStateOf(0)
    var next = mutableStateOf("")

    // TODO Dummy values, replace with array of database query result
    val trackCards = List(12) { TracksViewModel.TrackCard(0, "Name", "Location", R.drawable.ic_launcher_foreground) }

    data class Point (
        val latlon: Pair<Double, Double>,
        val name: String,
        var isPassed: Boolean
    )

    suspend fun getProximityFlow(latlon: Pair<Double, Double>): Flow<Double> {
        return location.getProximityFlow(latlon)
    }

    suspend fun getAverageLocation(): Pair<Double, Double>{
        return location.getAverageLocation(6)
    }
}