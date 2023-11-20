package cmpt370.group12.laptracker.viewmodel.main

import androidx.lifecycle.ViewModel
import cmpt370.group12.laptracker.R
import cmpt370.group12.laptracker.view.main.MapsViewModel

class TracksViewModel(backend: MapsViewModel) :ViewModel() {
    // TODO add all data and states values required for TracksView composable functions
    // TODO (if applicable) retrieve data from database (model)

    data class TrackCard(
        val id: Int, // track reference in database
        val name: String,
        val location: String,
        val mapSnippet: Int
    )

    // TODO Dummy values, replace with array of database query result
    val trackCards = List(12) { TrackCard(0, "Name", "Location", R.drawable.ic_launcher_foreground) }


}