package cmpt370.group12.laptracker.ui.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmpt370.group12.laptracker.domain.model.MapPoint

import cmpt370.group12.laptracker.domain.repository.LapTrackerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val repository: LapTrackerRepository
): ViewModel() {


    private val _trackstate = mutableStateOf(TrackState())
    private val _mapstate = mutableStateOf(MapState())
    private val _appstate = mutableStateOf(AppState())
    val trackstate: State<TrackState> = _trackstate
    val mapstate: State<MapState> = _mapstate
    val appstate: State<AppState> = _appstate
    var mapPointsRefreshByTrackIdJob : Job? = null


    init {
        refreshMapPointsbyTrackId()
    }

    private fun refreshMapPointsbyTrackId() {
        // This is SUPER important
        // so because we are calling a coroutine that uses a flow that is dependent on the
        // currenttrackid.  If we don't cancel the job, a new one will start, so we will have
        // multiple instances of the same coroutine with difference currentTrackId's being sent'
        // messes eveything up
        // so let's kill the old job, and start a new flow based on the currenttrackID
        mapPointsRefreshByTrackIdJob?.cancel()
        mapPointsRefreshByTrackIdJob = viewModelScope.launch {


            repository.MapPoints_getByTrackId(trackstate.value.currentTrackId)

                .collectLatest { mappoints ->
                    _trackstate.value = trackstate.value.copy(
                        mapPoints = mappoints
                    )
                }
            } }


    fun change_map(mapId: Int) {
        _trackstate.value = trackstate.value.copy(
            currentTrackId = mapId
        )
        refreshMapPointsbyTrackId()
    }

    fun onEvent(event: AppEvent) {
        when (event) {
            is AppEvent.ToggleMap -> {
                if (trackstate.value.currentTrackId == 0) {
                    change_map(1)
                } else {
                    change_map(0)
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

