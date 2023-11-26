@file:Suppress("SpellCheckingInspection", "unused")

package cmpt370.group12.laptracker.viewmodel


import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import cmpt370.group12.laptracker.R
import cmpt370.group12.laptracker.model.domain.location.LocationTracker
import cmpt370.group12.laptracker.model.domain.model.Achievement
import cmpt370.group12.laptracker.model.domain.model.MapPoint
import cmpt370.group12.laptracker.model.domain.model.Track
import cmpt370.group12.laptracker.model.domain.repository.LapTrackerRepository
import cmpt370.group12.laptracker.presentation.AppEvent
import cmpt370.group12.laptracker.presentation.AppState
import cmpt370.group12.laptracker.presentation.MapState
import cmpt370.group12.laptracker.presentation.TrackState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@Suppress("FunctionName")
@HiltViewModel
class GlobalViewModel @Inject constructor(
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
    //  FlowMapPointsbyCurrentTrackId_Start()
     //   FlowRunTimesbyCurrentRunId_Start()
       FlowTrackList_Start()
    //  FlowRunsListbyTrackId_Start()
    //  FlowAchievementList_Start()
   //  AchievementSetDefaults()
    }
// When it Ends
    override fun onCleared() {
        FlowCurrentLocation_Stop()
        FlowMapPointsbyCurrentTrackId_Stop()
        FlowRunTimesbyCurrentRunId_Stop()
        FlowTrackList_Stop()
        FlowRunsListbyTrackId_Stop()
        FlowAchievementList_Stop()
    }

    fun addAchievement(ac: Achievement){
        viewModelScope.launch {
            repository.Achievement_insert(ac)

        }
    }


    fun AchievementSetDefaults() {
        //TODO insert real achievements (dont change null id)
        val achievements = listOf(
            Achievement(null, "name", "desc", false, R.drawable.ic_launcher_foreground, 0),
            Achievement(null, "name", "desc", false, R.drawable.ic_launcher_foreground, 0),
            Achievement(null, "name", "desc", false, R.drawable.ic_launcher_foreground, 0),
            Achievement(null, "name", "desc", false, R.drawable.ic_launcher_foreground, 0),
            Achievement(null, "name", "desc", false, R.drawable.ic_launcher_foreground, 0),
            Achievement(null, "name", "desc", false, R.drawable.ic_launcher_foreground, 0)
        )

        viewModelScope.launch {
            if (repository.Achievement_getAll().isEmpty()) {
                achievements.forEach { achievement ->
                    repository.Achievement_insert(achievement)
                }
            }
        }
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
    Log.d("TEST","MAP: ${trackstate.value.mapPoints[0].latitude.toString()}"    )

}
    private fun FlowCurrentLocation_Start() {
        if (!_appstate.value.flowCurrentLocationActive && (_appstate.value.flowCurrentLocationJob == null)) {
            val lifejob: Job = viewModelScope.launch {
                locationTracker.currentLocationFlow(1)
                    .collectLatest {

                            _mapstate.value = mapstate.value.copy(
                                previousLocation = mapstate.value.currentLocation,
                                currentLocation = it
                            )


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
    private fun FlowAchievementList_Start(){
        if (!_appstate.value.flowAchievementListActive && (_appstate.value.flowAchievementListJob == null)) {
            val lifejob: Job = viewModelScope.launch {
                repository.Achievement_getAllFlow()

                    .collectLatest { achievements  ->
                        _appstate.value = appstate.value.copy(
                            achievements = achievements
                        )
                    }

            }
            _appstate.value = appstate.value.copy(
                flowAchievementListJob =  lifejob,
                flowAchievementListActive = true,
            )
        }

    }
    private fun FlowAchievementList_Stop(){
        _appstate.value.flowAchievementListJob?.cancel()
        _appstate.value = appstate.value.copy(
            flowAchievementListJob = null,
            flowAchievementListActive = false,
        )
    }
    private fun FlowAchievementList_Restart(){
        FlowAchievementList_Stop()
        FlowAchievementList_Start()
    }

    private fun FlowMapPointsbyCurrentTrackId_Start() {
        if (!_appstate.value.flowCurrentTrackMapPointActive && (_appstate.value.flowCurrentTrackMapPointJob == null)) {
            val lifejob: Job = viewModelScope.launch {
                repository.MapPoints_getByTrackId(_trackstate.value.currentTrackId.toInt())

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
                repository.Runs_getByTrackId(_trackstate.value.currentTrackId.toInt())

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


    private fun FlowRunTimesbyCurrentRunId_Start() {
        if (!_appstate.value.flowCurrentRunTimesActive && (_appstate.value.flowCurrentRunTimesJob == null)) {
             val lifejob: Job = viewModelScope.launch {
                repository.RunTimes_getByRunId(_trackstate.value.currentRunId.toInt())

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
                if (trackstate.value.currentTrackId.toInt() == 0) {
                    Change_Track(1)
                } else {
                    Change_Track(0)
                }

            }

            is AppEvent.OnMapLongClick -> {

                    Create_MapPoint(
                        MapPoint(
                            null,
                            trackstate.value.currentTrackId.toInt(),
                            event.latLng.latitude,
                            event.latLng.longitude,
                            "",
                            0

                        ))
            }

            is AppEvent.OnInfoWindowLongClick -> {
                viewModelScope.launch {
                    Delete_MapPoint(event.mappoint)
                }
            }

            else -> {}
        }
    }




    fun Change_Track(trackid: Long) {
        _trackstate.value = trackstate.value.copy(
            currentTrackId = trackid
        )
        FlowMapPointsbyCurrentTrackId_Restart()
        Set_isMapLoaded(true)
    }
    fun Change_Run(runId: Long) {
        _trackstate.value = trackstate.value.copy(
            currentRunId = runId
        )
        FlowRunTimesbyCurrentRunId_Restart()
    }

    fun Create_Track(track: Track){
        viewModelScope.launch {
            val newtrackid = repository.Track_insert(track)
            Log.d("TEST",newtrackid.toString())

            Change_Track(newtrackid)
            Log.d("TEST",trackstate.value.currentTrackId.toString())
                }

    }

    fun Create_MapPoint(mapPoint: MapPoint){
        viewModelScope.launch {

            val mapPointSequenced: MapPoint = mapPoint.copy(
             sequenceNumber = trackstate.value.mapPoints.size
            )

            val newMapPointId = repository.MapPoint_insert(mapPointSequenced)

        }

    }
    fun Delete_MapPoint(mapPoint: MapPoint){
        viewModelScope.launch {
            val plist : List<MapPoint> = trackstate.value.mapPoints
            var index : Int = 0
            // Delete The Point
            repository.MapPoint_delete(mapPoint)
            // Make a Copy of the List and then sort it by sequencenumber, so everything is
            // accending.
            val plist2 = plist.filterNot {it == mapPoint}.sortedBy { it.sequenceNumber }

            // Go through each and starting with zero, resequence
            plist2.forEach(){
                val newmapPoint: MapPoint = it.copy(sequenceNumber =  index++)
                repository.MapPoint_insert(newmapPoint)

            }
        }

    }


    fun Set_isStartCardVisible(value: Boolean){
        _appstate.value = appstate.value.copy(
            isStartUpCardVisible = value
        )
    }

    fun Set_isCreateTrackVisible(value: Boolean){
        _appstate.value = appstate.value.copy(
            isCreateTrackCardVisible = value
        )
    }

    fun Set_isFollowMe(value: Boolean){
        _mapstate.value = mapstate.value.copy(
            isFollowMe = value
        )
    }

    fun Set_mainNavController(nav: NavController){
        _appstate.value = appstate.value.copy(
            mainNavController = nav
        )
    }
    fun Set_isMapLoaded(value: Boolean){
        _appstate.value = appstate.value.copy(
            isMapLoaded = value
        )
    }











}

