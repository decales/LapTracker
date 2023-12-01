package cmpt370.group12.laptracker.viewmodel

import android.location.Geocoder
import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmpt370.group12.laptracker.Achievements
import cmpt370.group12.laptracker.R
import cmpt370.group12.laptracker.model.LocationClient
import cmpt370.group12.laptracker.model.domain.model.Achievement
import cmpt370.group12.laptracker.model.domain.model.MapPoint
import cmpt370.group12.laptracker.model.domain.model.Track
import cmpt370.group12.laptracker.model.domain.repository.LapTrackerRepository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    val db: LapTrackerRepository
) : ViewModel() {

    lateinit var locationClient: LocationClient // Passed in nav controller in MainActivity

    enum class ViewState {
        ChooseMode,
        NewTrack,
        LoadTrack,
        SaveTrack,
        InRun,
        PostRun,
        NoServices
    }

    var viewState: ViewState by mutableStateOf(ViewState.ChooseMode)

    // UI Variables
    var statsBarToggled by mutableStateOf(false)
    var setToggle = mutableStateOf(false)
    val scope = CoroutineScope(Dispatchers.Main)
    var textSetPoints = "Set start"
    var isLoading = mutableStateOf(false)
    var isToggled = mutableStateOf(false)
    var color = Color(0, 153, 0)
    var textTracking = "Start Tracking"
    var thread = mutableStateOf<Job?>(null)

    //Achievements
    val achievements = Achievements()
    var updateAchievements by mutableStateOf(false)
    var achieved by mutableStateOf(false)


    // Tracking variables
    var runStarted by mutableStateOf(false)
    var mapPoints:SnapshotStateList<LatLng> = mutableStateListOf()
    var currentLocation: Location? by mutableStateOf(null)
    var nextPoint: LatLng by mutableStateOf(LatLng(0.0, 0.0))
    var lapsCompleted by mutableIntStateOf(0)
    var lapCount by mutableIntStateOf(3)


    //Map variables
    var mapIsEnabled:Boolean by mutableStateOf(false)
    var mapProperties:MapProperties by mutableStateOf(MapProperties())
    var mapSettings:MapUiSettings by mutableStateOf(MapUiSettings(
        zoomControlsEnabled = false,
        zoomGesturesEnabled = false,
        scrollGesturesEnabled = false,
        scrollGesturesEnabledDuringRotateOrZoom = false
    ))


    fun enableMap() {
        mapSettings = MapUiSettings()
    }


    fun disableMap() {
        mapSettings = MapUiSettings(
            zoomControlsEnabled = false,
            zoomGesturesEnabled = false,
            scrollGesturesEnabled = false,
            scrollGesturesEnabledDuringRotateOrZoom = false
        )
    }


    fun launchChooseMode() {
        mapPoints.clear()
        viewState = ViewState.ChooseMode
        mapIsEnabled = false
        disableMap()
    }


    fun launchNewTrack() {
        viewModelScope.launch {
            locationClient.startLocationFlow(0.5)
            currentLocation = locationClient.getCurrentLocation()
            viewState = ViewState.NewTrack
            mapIsEnabled = true
        }
    }

    fun launchInRun() {
        viewState = ViewState.InRun
        disableMap()
    }


    fun panMapCamera(cameraState: CameraPositionState) {
        viewModelScope.launch {
            while (!mapIsEnabled) {
                cameraState.animate(CameraUpdateFactory.scrollBy(1000F, 0F), 60000)
            }
        }
    }


    fun panMapCameraToCurrentLocation(cameraState: CameraPositionState) {
        disableMap()
        viewModelScope.launch {
            async {
                cameraState.animate(
                    CameraUpdateFactory.newCameraPosition
                        (CameraPosition(LatLng(currentLocation!!.latitude, currentLocation!!.longitude), 18.0F, 0.0F, 0.0F)), 2000
                )
            }.invokeOnCompletion {
                currentLocation = locationClient.locationFlow.value
                enableMap()
            }
        }
    }

    fun saveTrack(nameInput: String, geoCoder: Geocoder) {
        viewModelScope.launch{
            val location = geoCoder.getFromLocation(mapPoints.first().latitude, mapPoints.first().longitude, 1)
            val locationStr = "${location?.first()?.locality}\n${location?.first()?.countryName}"
            val id = db.Track_insert(Track(null, nameInput, locationStr, "", R.drawable.ic_launcher_foreground))
            mapPoints.forEachIndexed { index, point ->
                db.MapPoint_insert(MapPoint(null, id.toInt(), point.latitude, point.longitude, "name?", index))
            }
        }
    }

    fun getAchievementState(achievementName: String) {
        viewModelScope.launch {
            val allAchievements = db.Achievement_getAll()
            allAchievements.forEach{ achi -> if (achi.name == achievementName) {
                achieved = achi.achieved
                delay(2000)
                db.Achievement_insert(Achievement(achi.id, achi.name, achi.description, true, achi.iconID, LocalDateTime.now().year.toLong()))
            } }
        }
    }
}