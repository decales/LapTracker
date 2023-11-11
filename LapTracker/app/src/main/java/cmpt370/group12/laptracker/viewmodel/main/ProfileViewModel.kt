package cmpt370.group12.laptracker.viewmodel.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import cmpt370.group12.laptracker.R

class ProfileViewModel: ViewModel() {
    // TODO add all data and states values required for ProfileView composable functions
    // TODO (if applicable) retrieve data from database (model)

    data class ProfileTab(
        val text: String,
        val icon: Int,
    )

    var currentTab by mutableIntStateOf(0)

    val profileTabs = listOf(
        ProfileTab("Statistics", R.drawable.ic_launcher_foreground),
        ProfileTab("Achievements", R.drawable.ic_launcher_foreground)
    )

    fun setTab() {
        currentTab = if (currentTab == 0) 1 else 0
    }
}