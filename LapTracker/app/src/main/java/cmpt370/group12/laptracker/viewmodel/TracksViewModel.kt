package cmpt370.group12.laptracker.viewmodel

import DaoRepositoryImplementation
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmpt370.group12.laptracker.Achievements
import cmpt370.group12.laptracker.R
import cmpt370.group12.laptracker.model.data.mapper.DaoRepository
import cmpt370.group12.laptracker.model.domain.model.Achievement
import cmpt370.group12.laptracker.model.domain.model.Track
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class TracksViewModel @Inject constructor(
    val dao: DaoRepository
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
    var currentTrackDetails: Track by mutableStateOf(Track(null, "", "", "", 0, emptyList(), emptyList()))
    var trackDetailsVisible by mutableStateOf(false)
    var deleteConfirmationVisible by mutableStateOf(false)
    var deleteModeToggled by mutableStateOf(false)

    fun toggleTrackDetails(selectedTrack: Track) {
        viewModelScope.launch{
            trackDetailsVisible = !trackDetailsVisible
            currentTrackDetails = selectedTrack
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
                if (trackCard.isSelected.value) dao.trackDelete(trackCard.track)
            }
            fetchTracks()
            toggleDeleteConfirmation()
        }
    }

    fun deleteCurrentTrack() {
        viewModelScope.launch {
            dao.trackDelete(currentTrackDetails)
            fetchTracks()
            toggleDeleteConfirmation()
            trackDetailsVisible = !trackDetailsVisible
        }
    }

    suspend fun fetchTracks() {
        viewModelScope.launch {
            tracksCards.value = dao.trackGetAll().map { track ->
                TrackCard(track, mutableStateOf(false))
            }
        }
    }


    fun addTrack() { // TODO temporary for testing, remove later
        viewModelScope.launch{
            val i = (Math.random() * 1000).toInt()
            dao.trackInsert(Track(null, "test $i", "test $i", "test comment", R.drawable.ic_launcher_foreground,
                listOf(Pair(52.1331886, -106.6348112), Pair(52.13319, -106.63482)),
                listOf(Pair(10L, 0L), Pair(10L, 0L))))
            fetchTracks()
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