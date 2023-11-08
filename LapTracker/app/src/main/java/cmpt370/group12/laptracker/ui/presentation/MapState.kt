package cmpt370.group12.laptracker.ui.presentation

import cmpt370.group12.laptracker.domain.model.Achievement
import cmpt370.group12.laptracker.domain.model.Comment
import com.google.maps.android.compose.MapProperties
import cmpt370.group12.laptracker.domain.model.MapPoint
import cmpt370.group12.laptracker.domain.model.Runs
import cmpt370.group12.laptracker.domain.model.Runtimes
import cmpt370.group12.laptracker.domain.model.Track
import com.google.maps.android.compose.MapUiSettings
/*
Split Up The States.
Went to a 3 Tier Approach.
MapState : This will deal with Map Related States and will also store our current Lat/Long.
TrackState: Track Related States.
curentTrackId   our current track's primary key
currentRunId  the current runs primary key
mapPoints  the list of mappoints ONLY for the currentTrackId
runtimes list of runtimes ONLY for the currentRunId
AppState
achievements list of all achievements (should be so small, might as well expose the whole thing)
tracks list of all tracks (this is actually perfect to use to populate a dropdown)
mapPoints list of all mappoints ( maybe for debugging)
run list of all runs ( maybe for debugging)
runtimes list of all runtimes ( maybe for debugging)
*/

data class MapState(
    val properties: MapProperties = MapProperties(),
    val uiSettings: MapUiSettings = MapUiSettings(),
    val currentLatitude: Long = 0,
    val currentLongitude: Long = 0


)
data class TrackState(
    val currentTrackId: Int = 0,
    val currentRunId: Int = 0,
    val comments: List<Comment> = emptyList(),
    val mapPoints: List<MapPoint> = emptyList(),
    val runtimes: List<Runtimes> = emptyList()
)

data class AppState(
    val achievements: List<Achievement> = emptyList(),
    val tracks: List<Track> = emptyList(),
    val mapPoints: List<MapPoint> = emptyList(),
    val runs: List<Runs> = emptyList(),
    val runtimes: List<Runtimes> = emptyList()

    )

