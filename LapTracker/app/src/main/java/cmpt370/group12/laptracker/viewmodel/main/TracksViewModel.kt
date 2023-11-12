package cmpt370.group12.laptracker.viewmodel.main

import androidx.lifecycle.ViewModel
import cmpt370.group12.laptracker.R

class TracksViewModel :ViewModel() {
    // TODO add all data and states values required for TracksView composable functions
    // TODO (if applicable) retrieve data from database (model)

    val trackCards = listOf( // TODO Dummy values, replace with array of database query result
        TrackCard(0, "Name", "Location", R.drawable.ic_launcher_foreground),
        TrackCard(0, "Name", "Location", R.drawable.ic_launcher_foreground),
        TrackCard(0, "Name", "Location", R.drawable.ic_launcher_foreground),
        TrackCard(0, "Name", "Location", R.drawable.ic_launcher_foreground),
    )

    data class TrackCard(
        val id: Int, // track reference in database
        val name: String,
        val location: String,
        val mapSnippet: Int
    )
}