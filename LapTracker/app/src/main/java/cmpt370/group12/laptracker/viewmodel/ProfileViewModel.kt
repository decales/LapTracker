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

    fun setPage(index: Int) {
        currentPage = index
    }

    fun toggleAchievementDetails(selectedAchievement: Achievement) {
        achievementDetailsVisible = !achievementDetailsVisible
        this.selectedAchievement = selectedAchievement
    }

    private suspend fun fetchAchievements() {
        val fetched = db.Achievement_getAll()
        if (fetched.isNotEmpty()) achievements.value = fetched
        else { // On app install, populate db with achievements
            listOf(
                Achievement(null, "name1", "desc", false, R.drawable.ic_launcher_foreground, 0),
                Achievement(null, "name2", "desc", false, R.drawable.ic_launcher_foreground, 0),
                Achievement(null, "name3", "desc", false, R.drawable.ic_launcher_foreground, 0),
                Achievement(null, "name4", "desc", false, R.drawable.ic_launcher_foreground, 0),
                Achievement(null, "name5", "desc", false, R.drawable.ic_launcher_foreground, 0),
                Achievement(null, "name6", "desc", false, R.drawable.ic_launcher_foreground, 0),
                Achievement(null, "name6", "desc", false, R.drawable.ic_launcher_foreground, 0),
                Achievement(null, "name6", "desc", false, R.drawable.ic_launcher_foreground, 0),
                Achievement(null, "name6", "desc", false, R.drawable.ic_launcher_foreground, 0),
                Achievement(null, "name6", "desc", false, R.drawable.ic_launcher_foreground, 0),
                Achievement(null, "name6", "desc", false, R.drawable.ic_launcher_foreground, 0),
                Achievement(null, "name6", "desc", false, R.drawable.ic_launcher_foreground, 0)
            ).forEach { achievement -> db.Achievement_insert(achievement) }
            achievements.value = db.Achievement_getAll()
        }
    }

    init {
        viewModelScope.launch {
            fetchAchievements()
        }
    }
}