package cmpt370.group12.laptracker.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmpt370.group12.laptracker.R
import cmpt370.group12.laptracker.model.domain.model.Achievement
import cmpt370.group12.laptracker.model.domain.repository.LapTrackerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val db: LapTrackerRepository
): ViewModel() {

    val achievements: MutableState<List<Achievement>> = mutableStateOf(emptyList())
    var selectedAchievement: Achievement by mutableStateOf(Achievement(null, "", "", false, R.drawable.ic_launcher_foreground, 0))
    var achievementDetailsVisible by mutableStateOf(false)
    var currentPage by mutableIntStateOf(0)

    //Lap stats
    var avgLapTime = 0.0
    var maxLapTime = 0.0
    var minLapTime = 0.0


    fun setPage(index: Int) {
        currentPage = index
    }

    fun toggleAchievementDetails(selectedAchievement: Achievement) {
        achievementDetailsVisible = !achievementDetailsVisible
        this.selectedAchievement = selectedAchievement
    }

    suspend fun fetchAchievements() {
        val fetched = db.Achievement_getAll()
        if (fetched.isNotEmpty()) achievements.value = fetched
        else { // On app install, populate db with achievements
            listOf(
                Achievement(null, "Created First Track", "Created your first track!", false, R.drawable.ic_launcher_foreground, 0),
                Achievement(null, "Load First Track", "Loaded your first track", false, R.drawable.ic_launcher_foreground, 0),
                Achievement(null, "Checked First Track", "You looked at a previously made track", false, R.drawable.ic_launcher_foreground, 0)
            ).forEach { achievement -> db.Achievement_insert(achievement) }
            achievements.value = db.Achievement_getAll()
        }
    }

    fun getLapStats() {
        viewModelScope.launch {
            if (!db.Runs_getAll().isEmpty()) {
                maxLapTime = (db.Runs_getMax().endTime - db.Runs_getMax().startTime).toDouble()
                minLapTime = (db.Runs_getMin().endTime - db.Runs_getMin().startTime).toDouble()
                var total = 0.0

                db.Runs_getAll().forEach { run ->
                    total += (run.endTime - run.startTime)
                }
                avgLapTime = total / db.Runs_getAll().size
            }
        }
    }

    init {
        viewModelScope.launch {
            fetchAchievements()
        }
    }
}