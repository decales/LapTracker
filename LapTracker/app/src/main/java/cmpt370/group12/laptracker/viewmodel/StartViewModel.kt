package cmpt370.group12.laptracker.viewmodel

import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import cmpt370.group12.laptracker.R
import cmpt370.group12.laptracker.model.LocationClient
import cmpt370.group12.laptracker.model.domain.repository.LapTrackerRepository
import cmpt370.group12.laptracker.view.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val db: LapTrackerRepository
) : ViewModel() {

    lateinit var locationClient: LocationClient // Passed in nav controller in MainActivity

    // TODO add all data and states values required for SettingsView composable functions
    // TODO (if applicable) retrieve data from database (model)
    var createRace = mutableStateOf(false)
    var pickTrack = mutableStateOf(false)
    var trackPicked = mutableStateOf(false)
    var points = mutableStateListOf<Point>()
    var setToggle = mutableStateOf(false)
    val scope = CoroutineScope(Dispatchers.Main)
    var textSetPoints = "Set start"
    var isLoading = mutableStateOf(false)
    var isToggled = mutableStateOf(false)
    var color = Color(0, 153, 0)
    var textTracking = "Start Tracking"
    var thread = mutableStateOf<Job?>(null)

    // Tracking UI variables
    var distance = mutableDoubleStateOf(0.0)
    var laps = mutableIntStateOf(0)
    var next = mutableStateOf("")

    data class TrackCard(
        val id: Int, // track reference in database
        val name: String,
        val location: String,
        val points: MutableList<Point>,
        val mapSnippet: Int
    )

    // TODO Dummy values, replace with array of database query result
    val trackCards = List(12) { TrackCard(0, "Name", "Location",
        mutableListOf(
            Point(Pair(52.132681649999995, -106.63488491666665), "Start", false),
            Point(Pair(52.132681649999995, -106.63488491666665), "L1", false),
            Point(Pair(52.132681649999995, -106.63488491666665), "L2", false),
            Point(Pair(52.132681649999995, -106.63488491666665), "L3", false),
            Point(Pair(52.132681649999995, -106.63488491666665), "L4", false),
            Point(Pair(52.132681649999995, -106.63488491666665), "L5", false)
        ),
        R.drawable.ic_launcher_foreground) }

    data class Point (
        val latlon: Pair<Double, Double>,
        val name: String,
        var isPassed: Boolean
    )

    suspend fun getProximityFlow(latlon: Pair<Double, Double>): Flow<Double>? {
        return this.locationClient.getProximityFlow(latlon)
    }

    suspend fun getAverageLocation(): Pair<Double, Double>{
        return this.locationClient.getAverageLocation(6)
    }
}