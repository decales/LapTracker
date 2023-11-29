package cmpt370.group12.laptracker.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import cmpt370.group12.laptracker.R
import cmpt370.group12.laptracker.model.domain.model.Comment
import cmpt370.group12.laptracker.model.domain.model.Runs
import cmpt370.group12.laptracker.model.domain.model.Track
import cmpt370.group12.laptracker.model.domain.repository.LapTrackerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
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
        var isSelected: MutableState<Boolean>
    )

    // Public
    val tracksCards: MutableState<List<TrackCard>> = mutableStateOf(emptyList())
    var currentTrackDetails: Track by mutableStateOf(Track(null, "", "", "",0))
    var currentTrackDetailsRuns: List<Runs> by mutableStateOf(emptyList())
    var trackDetailsVisible by mutableStateOf(false)
    var deleteConfirmationVisible by mutableStateOf(false)
    var deleteModeToggled by mutableStateOf(false)

    fun toggleDeleteMode() {
        deleteModeToggled = !deleteModeToggled
    }

    fun toggleTrackDetails(selectedTrack: Track) {
        viewModelScope.launch{
            trackDetailsVisible = !trackDetailsVisible
            currentTrackDetails = selectedTrack
            fetchTrackRuns()
        }
    }

    fun toggleTrackDetails() {
        trackDetailsVisible =!trackDetailsVisible
    }

    fun toggleSelectTrack(trackCard: TrackCard) {
        trackCard.isSelected.value = !trackCard.isSelected.value
    }

    fun toggleDeleteConfirmation() {
        deleteConfirmationVisible = !deleteConfirmationVisible
    }

    fun deleteSelectedTracks() {
        viewModelScope.launch {
            tracksCards.value.forEach { trackCard ->
                if (trackCard.isSelected.value) db.Track_delete(trackCard.track)
            }
            fetchTracks()
            toggleDeleteConfirmation()
        }
    }

    fun deleteCurrentTrack() {
        viewModelScope.launch {
            db.Track_delete(currentTrackDetails)
            fetchTracks()
            toggleDeleteConfirmation()
            toggleTrackDetails()
        }
    }

    private suspend fun fetchTracks() {
        tracksCards.value = db.Track_getAll().map { track ->
            TrackCard(track, mutableStateOf(false))
        }
    }

    private suspend fun fetchTrackRuns() {
        viewModelScope.launch {
            currentTrackDetailsRuns = db.Runs_getByTrackId(currentTrackDetails.id!!)
        }
    }

    fun addTrack() { // TODO temporary for testing, remove later
        viewModelScope.launch{
            val i = (Math.random() * 1000).toInt()
            db.Track_insert(Track(null, "test $i", "test $i", "", R.drawable.ic_launcher_foreground))
            fetchTracks()
        }
    }

    init {
        viewModelScope.launch {
            fetchTracks()
        }
    }
}

fun getTrack(trackID: Int): Track? {
    /* TODO optional - this function just returns a Track object to make implementing the other functions easier*/
    return null
}

fun GetAverageSpeed(trackID: Int): String {
    /* TODO build this function to return the average speed for a specific track */
    return "12"
}

fun GetTotalDistance(trackID: Int) : String {
    /* TODO build this function to return the total distance travelled on a specific track */
    return "10"
}

fun GetTotalTimeSpentOnTrack(trackID: Int) : String {
    /* TODO build this function to return the total time spent on a specific track */
    return "test 15h 30min"
}

fun getComment(trackID: Int): String {
    /* TODO Declan finish this function */
    return "comment"
}

fun setComment(comment: Comment) {
    /* TODO Declan finish this function */
}