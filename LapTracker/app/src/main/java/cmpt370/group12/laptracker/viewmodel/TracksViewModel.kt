package cmpt370.group12.laptracker.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import cmpt370.group12.laptracker.R
import cmpt370.group12.laptracker.model.domain.model.Achievement
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

    //val tracks = List(12) { Track(0, "Name", "Location", R.drawable.ic_launcher_foreground) } // Test tracks, delete later
    val tracks: MutableState<List<Track>> = mutableStateOf(emptyList())
    var selectedTrack: Track by mutableStateOf(Track(null, "", "", 0))
    var selectedTrackRuns: List<Runs> by mutableStateOf(emptyList())
    var trackDetailsVisible by mutableStateOf(false)


    fun toggleTrackDetails(selectedTrack: Track) {
        trackDetailsVisible = !trackDetailsVisible
        this.selectedTrack = selectedTrack
        fetchTrackRuns()
    }

    private fun fetchTracks() {
        viewModelScope.launch {
            tracks.value = db.Track_getAll()
        }
    }

    private fun fetchTrackRuns() {
        viewModelScope.launch {
            selectedTrackRuns = db.Runs_getByTrackId(selectedTrack.id!!)
        }
    }

    fun addTrack() {
        viewModelScope.launch{
            db.Track_insert(Track(null, "test", "test", R.drawable.ic_launcher_background))
        }
        fetchTracks()
    }

    init {
        fetchTracks()
    }
}