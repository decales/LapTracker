package cmpt370.group12.laptracker.viewmodel

import DaoRepositoryImplementation
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmpt370.group12.laptracker.R
import cmpt370.group12.laptracker.model.data.mapper.DaoRepository
import cmpt370.group12.laptracker.model.domain.model.Achievement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val dao: DaoRepository
): ViewModel() {

    val achievements: MutableState<List<Achievement>> = mutableStateOf(emptyList())
    var selectedAchievement: Achievement by mutableStateOf(Achievement(null, "", "", false, R.drawable.ic_launcher_foreground, 0))
    var achievementDetailsVisible by mutableStateOf(false)
    var currentPage by mutableIntStateOf(0)

    //Lap stats
    var avgLapTime by mutableDoubleStateOf(0.0)
    var maxLapTime by mutableDoubleStateOf(0.0)
    var minLapTime by mutableDoubleStateOf(0.0)

    fun setPage(index: Int) {
        currentPage = index
    }

    fun toggleAchievementDetails(selectedAchievement: Achievement) {
        achievementDetailsVisible = !achievementDetailsVisible
        this.selectedAchievement = selectedAchievement
    }

    suspend fun fetchAchievements() {
        val fetched = dao.achievementGetAll()
        if (fetched.isNotEmpty()) achievements.value = fetched
        else { // On app install, populate db with achievements
            listOf(
                Achievement(null, "Created First Track", "Created your first track!", false, R.drawable.ic_launcher_foreground, 0),
                Achievement(null, "Load First Track", "Loaded your first track", false, R.drawable.ic_launcher_foreground, 0),
                Achievement(null, "Checked First Track", "You looked at a previously made track", false, R.drawable.ic_launcher_foreground, 0)
            ).forEach { achievement -> dao.achievementUpdate(achievement) }
            achievements.value = dao.achievementGetAll()
        }
    }

    fun getLapStats() {
        viewModelScope.launch {
            val tracks = dao.trackGetAll()

            if (tracks.isNotEmpty()) {
                maxLapTime = (tracks
                    .flatMap { it.lapTimes ?: emptyList() }
                    .maxOfOrNull { lap -> lap.second - lap.first }
                    ?: Double.MIN_VALUE).toDouble()

                minLapTime = (tracks
                    .flatMap { it.lapTimes ?: emptyList() }
                    .minOfOrNull { lap -> lap.second - lap.first }
                    ?: Double.MAX_VALUE).toDouble()

                var total = 0.0
                var count = 0

                tracks.forEach { track ->
                    track.lapTimes?.forEach { lap ->
                        lap?.let {
                            total += (it.second - it.first)
                            count++
                        }
                    }
                }
                if (count > 0) {
                    avgLapTime = total / count
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            fetchAchievements()
        }
    }
}