package cmpt370.group12.laptracker.viewmodel.main

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

    // Variable and object

    var currentPage by mutableIntStateOf(0)

    val profileTabs = listOf(
        ProfileTab("Statistics", R.drawable.ic_launcher_foreground),
        ProfileTab("Achievements", R.drawable.ic_launcher_foreground)
    )

    // Methods

    fun setPage(index: Int) {
        currentPage = index
    }
}