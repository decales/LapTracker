package cmpt370.group12.laptracker.viewmodel

import DaoRepositoryImplementation
import android.location.Geocoder
import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmpt370.group12.laptracker.Achievements
import cmpt370.group12.laptracker.R
import cmpt370.group12.laptracker.model.LocationClient
import cmpt370.group12.laptracker.model.data.mapper.DaoRepository
import cmpt370.group12.laptracker.model.domain.model.Achievement
import cmpt370.group12.laptracker.model.domain.model.Track
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.time.Duration

@HiltViewModel
class StartViewModel @Inject constructor(
    val dao: DaoRepository
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

    // UI variables
    var viewState: ViewState by mutableStateOf(ViewState.ChooseMode)
    var statsBarVisible by mutableStateOf(false)
    var setToggle = mutableStateOf(false)
    var isLoading = mutableStateOf(false)
    var color = Color(0, 153, 0)
    var thread by mutableStateOf<Job?>(null)
    var isSaved by mutableStateOf(false)


    //Achievements
    val achievements = Achievements()
    var updateAchievements by mutableStateOf(false)
    var achieved by mutableStateOf(false)


    // Tracking variables
    var runStarted by mutableStateOf(false)
    var mapPoints: SnapshotStateList<Pair<Double,Double>> = mutableStateListOf()
    var currentLocation: Location? by mutableStateOf(null)
    var nextPoint: Pair<Double,Double> by mutableStateOf(Pair(0.0, 0.0))
    var lapsCompleted by mutableIntStateOf(0)
    var lapCount by mutableIntStateOf(3)
    var lapTimes: SnapshotStateList<Pair<Long, Long>> =  mutableStateListOf()
    var startTime by mutableLongStateOf(0)
    var distance by mutableDoubleStateOf(0.0)


    //List of tracks variables
    var trackCards: List<TracksViewModel.TrackCard> by mutableStateOf(emptyList())


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
        mapProperties = MapProperties(

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
        disableMap()
        viewState = ViewState.InRun
        mapIsEnabled = false
        viewModelScope.launch {
            locationClient.locationFlow.collect { location ->
                currentLocation =  location
            }
        }
    }


    fun startRacing() {
        runStarted = true
        thread = viewModelScope.launch {
            while (lapsCompleted <= lapCount) {
                mapPoints.forEachIndexed { index, point ->
                    startTime = currentLocation?.time!!
                    nextPoint = mapPoints[index + 1]
                    locationClient.locationFlow
                        .map { locationClient.getProximity(Pair(it!!.latitude, it.longitude), nextPoint) }
                        .first {
                            distance = it
                            it < 3
                        }
                }
                lapsCompleted ++
                lapTimes.add(Pair(startTime, currentLocation!!.time))
                if (isSaved) {
                    val currentTrack = trackCards.last().track
                    currentTrack.lapTimes = lapTimes.toList()
                    dao.trackInsert(currentTrack)
                }
            }
            thread?.cancel()
            lapCount = 0
            viewState = ViewState.PostRun
        }
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
            val location = geoCoder.getFromLocation(mapPoints.first().first, mapPoints.first().second, 1)
            val locationStr = "${location?.first()?.locality}\n${location?.first()?.countryName}"
            dao.trackInsert(Track(null, nameInput, locationStr, "", R.drawable.ic_launcher_foreground, mapPoints, emptyList()))
        }
    }


    suspend fun fetchTracks() {
        trackCards = dao.trackGetAll().map { track ->
            TracksViewModel.TrackCard(track, mutableStateOf(false))
        }
    }


    fun getAchievementState(achievementName: String) {
        viewModelScope.launch {
            val allAchievements = dao.achievementGetAll()
            allAchievements.forEach{ achi -> if (achi.name == achievementName) {
                achieved = achi.achieved
                delay(2000)
                dao.achievementUpdate(Achievement(achi.id, achi.name, achi.description, true, achi.iconID, LocalDateTime.now().year.toLong()))
            } }
        }
    }
}