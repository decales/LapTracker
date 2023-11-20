package cmpt370.group12.laptracker.viewmodel.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import cmpt370.group12.laptracker.R
import cmpt370.group12.laptracker.domain.model.Achievement
import cmpt370.group12.laptracker.view.main.MapsViewModel

class ProfileViewModel(val backend: MapsViewModel): ViewModel() {
    // TODO add all data and states values required for ProfileView composable functions
    // TODO (if applicable) retrieve data from database (model)


    // Data classes
    data class ProfileTab(
        val text: String,
        val icon: Int,
    )
    
    //val backend.appstate.value.achievements: List<Achievement> = backend.appstate.value.achievements
    //var x = backend.appstate.value.achievements
    //val backend.appstate.value.achievements = mutableStateOf(backend.appstate.value.achievements)


    // Variables and objects

    var currentPage by mutableIntStateOf(0)

    val profileTabs = listOf(
        ProfileTab("Statistics", R.drawable.ic_launcher_foreground),
        ProfileTab("Achievements", R.drawable.ic_launcher_foreground)
    )

    //val achievements = List(18) { Achievement(0, "Name", "Description", R.drawable.ic_launcher_foreground, (it % 3 == 0), "Date unlocked") }
    //val achievements = backend.
    //val unlockedCount = backend.appstate.value.achievements.count { it.achieved }

    var achievementDetailsVisible by mutableStateOf(false)

    var currentAchievement: Achievement by mutableStateOf(Achievement(null, "", "", false, R.drawable.ic_launcher_foreground, 0))

    // Methods

    fun setPage(index: Int) {
        currentPage = index
    }

    fun toggleAchievementDetails(achievement: Achievement) {
        achievementDetailsVisible = !achievementDetailsVisible
        currentAchievement = achievement
    }
}