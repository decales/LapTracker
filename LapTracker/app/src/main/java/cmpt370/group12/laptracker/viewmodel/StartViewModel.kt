package cmpt370.group12.laptracker.viewmodel

import android.location.Location
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import cmpt370.group12.laptracker.R
import cmpt370.group12.laptracker.model.LocationClient
import cmpt370.group12.laptracker.model.domain.model.MapPoint
import cmpt370.group12.laptracker.model.domain.repository.LapTrackerRepository
import cmpt370.group12.laptracker.view.MainActivity
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelFutureOnCompletion
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

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
    var mapPoints = mutableListOf<MapPoint>()
    var currentLocation = mutableStateOf(LatLng(0.0, 0.0))
    var distance = mutableDoubleStateOf(0.0)
    var laps = mutableIntStateOf(0)
    var next = mutableStateOf("")

    //Map variables
    var cameraState = mutableStateOf(CameraPositionState())
    var cameraJob: MutableState<Job?> = mutableStateOf(null)
    var mapIsEnabled = mutableStateOf(false)
    var mapProperties = mutableStateOf(MapProperties())
    var mapSettings = mutableStateOf(MapUiSettings(
        zoomControlsEnabled = false,
        zoomGesturesEnabled = false,
        scrollGesturesEnabled = false,
        scrollGesturesEnabledDuringRotateOrZoom = false
    ))

    data class TrackCard(
        val id: Int, // track reference in database
        val name: String,
        val location: String,
        val points: MutableList<Point>,
        val mapSnippet: Int
    )

    data class Point (
        val latlon: Pair<Double, Double>,
        val name: String,
        var isPassed: Boolean

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
        R.drawable.ic_launcher_foreground)
    }


    suspend fun getProximityFlow(latlon: Pair<Double, Double>): Flow<Double>? {
        return this.locationClient.getProximityFlow(latlon)
    }


    suspend fun getAverageLocation(): LatLng {
        return this.locationClient.getAverageLocation(6)
    }

    fun enableMap() {
        mapIsEnabled.value = true
        mapSettings.value = MapUiSettings()
    }

    fun disableMap() {
        mapIsEnabled.value = false
        mapSettings.value = MapUiSettings(
            zoomControlsEnabled = false,
            zoomGesturesEnabled = false,
            scrollGesturesEnabled = false,
            scrollGesturesEnabledDuringRotateOrZoom = false
        )
    }


    fun prepareSettingPoints() {
        locationClient.startLocationFlow(1.0)
        viewModelScope.launch {

            val location = locationClient.getCurrentLocation()
            currentLocation.value = LatLng(location!!.latitude, location.longitude)

            mapIsEnabled.value = true
            async { panMapCameraToCurrentLocation() }.await()
            enableMap()
        }
    }


    fun panMapCamera() {
        cameraJob.value = viewModelScope.launch {
            while (!mapIsEnabled.value) {
                cameraState.value.animate(CameraUpdateFactory.scrollBy(1000F, 0F), 60000)
            }
        }
    }


    suspend fun panMapCameraToCurrentLocation() {
       cameraState.value.animate(
            CameraUpdateFactory.newCameraPosition(CameraPosition(currentLocation.value, 18.0F, 0.0F, 0.0F)), 2000
       )
    }
}