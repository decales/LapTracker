package cmpt370.group12.laptracker.viewmodel.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import cmpt370.group12.laptracker.R

class ProfileViewModel: ViewModel() {
    // TODO add all data and states values required for ProfileView composable functions
    // TODO (if applicable) retrieve data from database (model)

    // Data classes

    data class ProfileTab(
        val text: String,
        val icon: Int,
    )

    data class Achievement(
        val id: Int,
        val name: String,
        val icon: Int,
        val isAchieved: Boolean,
        val achievedDate: String
    )

    // Variables and objects

    var currentPage by mutableIntStateOf(0)

    val profileTabs = listOf(
        ProfileTab("Statistics", R.drawable.ic_launcher_foreground),
        ProfileTab("Achievements", R.drawable.ic_launcher_foreground)
    )

    // TODO Dummy values, replace with array of database query result
    val achievements = List(18) { Achievement(0, "Name", R.drawable.ic_launcher_foreground, (it % 3 == 0), "date") }

    val unlockedCount = achievements.count { it.isAchieved }

    // Methods

    fun setPage(index: Int) {
        currentPage = index
    }
}