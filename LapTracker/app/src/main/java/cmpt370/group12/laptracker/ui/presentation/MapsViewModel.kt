@file:Suppress("SpellCheckingInspection", "unused")

package cmpt370.group12.laptracker.ui.presentation


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmpt370.group12.laptracker.domain.location.LocationTracker
import cmpt370.group12.laptracker.domain.model.MapPoint
import cmpt370.group12.laptracker.domain.repository.LapTrackerRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject



@Suppress("FunctionName")
@HiltViewModel
class MapsViewModel @Inject constructor(
    private val repository: LapTrackerRepository,
    private val locationTracker: LocationTracker

): ViewModel() {


    private val _trackstate = mutableStateOf(TrackState())
    private val _mapstate = mutableStateOf(MapState())
    private val _appstate = mutableStateOf(AppState())
    val trackstate: State<TrackState> = _trackstate
    val mapstate: State<MapState> = _mapstate
    val appstate: State<AppState> = _appstate


//When it Starts up
    init {
        FlowCurrentLocation_Start()
        FlowMapPointsbyCurrentTrackId_Start()
        FlowRunTimesbyCurrentRunId_Start()
        FlowTrackList_Start()
        FlowRunsListbyTrackId_Start()

    }
// When it Ends
    override fun onCleared() {
        FlowCurrentLocation_Stop()
        FlowMapPointsbyCurrentTrackId_Stop()
        FlowRunTimesbyCurrentRunId_Stop()
        FlowTrackList_Stop()
        FlowRunsListbyTrackId_Stop()
    }
fun check_something(){
    //TODO: Does Nothing Yet.  This Will Run Functions At Each Update.
    // Check Distances, Acheivments, Etc.
    //println("KRIS - MapState: CurrentLocation ${mapstate.value.currentLocation.toString()}")
    println("KRIS - FLOWS Current Location:        ${appstate.value.flowCurrentLocationActive}")
    println("KRIS - FLOWS TrackList:               ${appstate.value.flowTrackListActive}")
    println("KRIS - FLOWS CurrentTrack Runs List : ${appstate.value.flowRunListByTrackIdActive}")
    println("KRIS - FLOWS CurrentRun Runtimes:     ${appstate.value.flowCurrentRunTimesActive}")
    println("KRIS - FLOWS CurrentTrack MapPoints:  ${appstate.value.flowCurrentTrackMapPointActive}")
    println("KRIS - --------------------------------------------------------------------------------------")

}
    private fun FlowCurrentLocation_Start() {
        if (!_appstate.value.flowCurrentLocationActive && (_appstate.value.flowCurrentLocationJob == null)) {

            val lifejob: Job = viewModelScope.launch {
                locationTracker.currentLocationFlow(1)
                    .collectLatest {

                            _mapstate.value = mapstate.value.copy(
                                currentLocation = it
                            )
                            check_something()

                        }

                    }


            _appstate.value = appstate.value.copy(
                flowCurrentLocationJob = lifejob,
                flowCurrentLocationActive = true,
            )
        }
    }
    private fun FlowCurrentLocation_Stop(){
        _appstate.value.flowCurrentLocationJob?.cancel()
        _appstate.value = appstate.value.copy(
            flowCurrentLocationJob = null,
            flowCurrentLocationActive = false,
        )
    }
    fun FlowCurrentLocation_Restart(){
        FlowCurrentLocation_Stop()
        FlowCurrentLocation_Start()
    }


    private fun FlowMapPointsbyCurrentTrackId_Start() {
        if (!_appstate.value.flowCurrentTrackMapPointActive && (_appstate.value.flowCurrentTrackMapPointJob == null)) {
            val lifejob: Job = viewModelScope.launch {
                repository.MapPoints_getByTrackId(_trackstate.value.currentTrackId)

                    .collectLatest { mappoints ->
                        _trackstate.value = trackstate.value.copy(
                            mapPoints = mappoints
                        )
                    }

            }
            _appstate.value = appstate.value.copy(
                flowCurrentTrackMapPointJob = lifejob,
                flowCurrentTrackMapPointActive = true,
            )
        }
    }

    private fun FlowMapPointsbyCurrentTrackId_Stop(){
        _appstate.value.flowCurrentTrackMapPointJob?.cancel()
        _appstate.value = appstate.value.copy(
            flowCurrentTrackMapPointJob = null,
            flowCurrentTrackMapPointActive = false,
        )
    }

    fun FlowMapPointsbyCurrentTrackId_Restart(){
        FlowMapPointsbyCurrentTrackId_Stop()
        FlowMapPointsbyCurrentTrackId_Start()
    }

    private fun FlowTrackList_Start(){
        if (!_appstate.value.flowTrackListActive && (_appstate.value.flowTrackListJob == null)) {
            val lifejob: Job = viewModelScope.launch {
                repository.Track_getAllFlow()

                    .collectLatest { tracks ->
                        _appstate.value = appstate.value.copy(
                            tracks = tracks
                        )
                    }

            }
            _appstate.value = appstate.value.copy(
                flowTrackListJob =  lifejob,
                flowTrackListActive = true,
            )
        }

    }
    private fun FlowTrackList_Stop(){
        _appstate.value.flowTrackListJob?.cancel()
        _appstate.value = appstate.value.copy(
            flowTrackListJob = null,
            flowTrackListActive = false,
        )
    }
    private fun FlowTrackList_Restart(){
        FlowTrackList_Stop()
        FlowTrackList_Start()
    }
    private fun FlowRunsListbyTrackId_Start(){
        if (!_appstate.value.flowRunListByTrackIdActive && (_appstate.value.flowRunListByTrackIdJob== null)) {
            val lifejob: Job = viewModelScope.launch {
                repository.Runs_getByTrackId(_trackstate.value.currentTrackId)

                    .collectLatest { runs ->
                        _trackstate.value = trackstate.value.copy(
                            runs = runs
                        )
                    }

            }
            _appstate.value = appstate.value.copy(
                flowRunListByTrackIdJob = lifejob,
                flowRunListByTrackIdActive = true,
            )
        }
    }
    private fun FlowRunsListbyTrackId_Stop() {
        _appstate.value.flowRunListByTrackIdJob?.cancel()
        _appstate.value = appstate.value.copy(
            flowRunListByTrackIdJob = null,
            flowRunListByTrackIdActive = false,
        )
    }
    private fun FlowRunsListbyTrackId_Restart(){
        FlowRunsListbyTrackId_Stop()
        FlowRunsListbyTrackId_Start()
    }



    fun Change_Map(mapId: Int) {
        _trackstate.value = trackstate.value.copy(
            currentTrackId = mapId
        )
        FlowMapPointsbyCurrentTrackId_Restart()
    }
    fun Change_Run(runId: Int) {
        _trackstate.value = trackstate.value.copy(
            currentRunId = runId
        )
        FlowRunTimesbyCurrentRunId_Restart()
    }



    private fun FlowRunTimesbyCurrentRunId_Start() {
        if (!_appstate.value.flowCurrentRunTimesActive && (_appstate.value.flowCurrentRunTimesJob == null)) {
             val lifejob: Job = viewModelScope.launch {
                repository.RunTimes_getByRunId(_trackstate.value.currentRunId)

                    .collectLatest { runtimes ->
                        _trackstate.value = trackstate.value.copy(
                            runtimes = runtimes
                        )
                    }

            }
            _appstate.value = appstate.value.copy(
                flowCurrentRunTimesJob = lifejob,
                flowCurrentRunTimesActive = true,
            )
        }
    }

    private fun FlowRunTimesbyCurrentRunId_Stop() {
        _appstate.value.flowCurrentRunTimesJob?.cancel()
        _appstate.value = appstate.value.copy(
            flowCurrentRunTimesJob = null,
            flowCurrentRunTimesActive = false,
        )
    }
    private fun FlowRunTimesbyCurrentRunId_Restart() {
        FlowRunTimesbyCurrentRunId_Stop()
        FlowRunTimesbyCurrentRunId_Start()
    }






// The Event Tree
    fun onEvent(event: AppEvent) {
        when (event) {
            is AppEvent.ToggleMap -> {
                if (trackstate.value.currentTrackId == 0) {
                    Change_Map(1)
                } else {
                    Change_Map(0)
                }

            }

            is AppEvent.OnMapLongClick -> {
                viewModelScope.launch {
                    repository.MapPoint_insert(
                        MapPoint(
                            null,
                            trackstate.value.currentTrackId,
                            event.latLng.latitude,
                            event.latLng.longitude,
                            "",
                            0

                        )

                    )
                }
            }

            is AppEvent.OnInfoWindowLongClick -> {
                viewModelScope.launch {
                    repository.MapPoint_delete(event.mappoint)
                }
            }

            else -> {}
        }
    }

}

