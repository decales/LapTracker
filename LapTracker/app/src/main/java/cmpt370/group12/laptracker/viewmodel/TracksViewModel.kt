package cmpt370.group12.laptracker.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmpt370.group12.laptracker.Achievements
import cmpt370.group12.laptracker.R
import cmpt370.group12.laptracker.model.domain.model.Achievement
import cmpt370.group12.laptracker.model.domain.model.Runs
import cmpt370.group12.laptracker.model.domain.model.Track
import cmpt370.group12.laptracker.model.domain.repository.LapTrackerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class TracksViewModel @Inject constructor(
    private val db: LapTrackerRepository
):ViewModel() {

    // TODO add all data and states values required for TracksView composable functions
    // TODO (if applicable) retrieve data from database (model)

    //Achievements
    val achievements = Achievements()
    var updateAchievement by mutableStateOf(false)
    var achieved by mutableStateOf(false)

    data class TrackCard(
        val track: Track,
        var isSelected: MutableState<Boolean>
    )

    val tracksCards: MutableState<List<TrackCard>> = mutableStateOf(emptyList())
    var currentTrackDetails: Track by mutableStateOf(Track(null, "", "", "", 0))
    var currentTrackDetailsRuns: List<Runs> by mutableStateOf(emptyList())
    var trackDetailsVisible by mutableStateOf(false)
    var deleteConfirmationVisible by mutableStateOf(false)
    var deleteModeToggled by mutableStateOf(false)

    fun toggleTrackDetails(selectedTrack: Track) {
        viewModelScope.launch{
            trackDetailsVisible = !trackDetailsVisible
            currentTrackDetails = selectedTrack
            fetchTrackRuns()
        }
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
            trackDetailsVisible = !trackDetailsVisible
        }
    }

    private suspend fun fetchTracks() {
        tracksCards.value = db.Track_getAll().map { track ->
            TrackCard(track, mutableStateOf(false))
        }
    }

    private suspend fun fetchTrackRuns() {
        viewModelScope.launch {
            currentTrackDetailsRuns = db.Runs_getByTrackId(currentTrackDetails.id)
        }
    }

    fun addTrack() { // TODO temporary for testing, remove later
        viewModelScope.launch{
            val i = (Math.random() * 1000).toInt()
            db.Track_insert(Track(null, "test $i", "test $i", "test comment", R.drawable.ic_launcher_foreground))
            fetchTracks()
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

    init {
        viewModelScope.launch {
            fetchTracks()
        }
    }
}