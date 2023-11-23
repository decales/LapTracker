package cmpt370.group12.laptracker.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmpt370.group12.laptracker.R
import cmpt370.group12.laptracker.model.domain.model.Runs
import cmpt370.group12.laptracker.model.domain.model.Track
import cmpt370.group12.laptracker.model.domain.repository.LapTrackerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TracksViewModel @Inject constructor(
    private val db: LapTrackerRepository
):ViewModel() {

    // TODO add all data and states values required for TracksView composable functions
    // TODO (if applicable) retrieve data from database (model)

    data class TrackCard(
        val track: Track,
        val isSelected: Boolean
    )

    val tracksCards: MutableState<List<TrackCard>> = mutableStateOf(emptyList())
    var currentTrackDetails: Track by mutableStateOf(Track(null, "", "", 0))
    var currentTrackDetailsRuns: List<Runs> by mutableStateOf(emptyList())
    var trackDetailsVisible by mutableStateOf(false)
    var deleteModeToggled by mutableStateOf(false)


    fun toggleDeleteMode() {
        deleteModeToggled = !deleteModeToggled
    }

    fun toggleTrackDetails(selectedTrack: Track) {
        trackDetailsVisible = !trackDetailsVisible
        this.currentTrackDetails = selectedTrack
        fetchTrackRuns()
    }

    private fun fetchTracks() {
        viewModelScope.launch {
            tracksCards.value = db.Track_getAll().map { track ->
                TrackCard(track, false)
            }
        }
    }

    private fun fetchTrackRuns() {
        viewModelScope.launch {
            currentTrackDetailsRuns = db.Runs_getByTrackId(currentTrackDetails.id!!)
        }
    }

    fun addTrack() {
        viewModelScope.launch{
            db.Track_insert(Track(null, "test", "test", R.drawable.ic_launcher_foreground))
        }
        fetchTracks()
    }

    init {
        fetchTracks()
    }
}