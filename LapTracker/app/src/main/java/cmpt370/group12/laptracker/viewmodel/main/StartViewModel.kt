package cmpt370.group12.laptracker.viewmodel.main

import androidx.lifecycle.ViewModel
import cmpt370.group12.laptracker.model.LocationClient
import cmpt370.group12.laptracker.view.main.MapsViewModel
import kotlinx.coroutines.flow.Flow

class StartViewModel(locationClient: LocationClient, backend: MapsViewModel) : ViewModel() {
    // TODO add all data and states values required for SettingsView composable functions
    // TODO (if applicable) retrieve data from database (model)
    var location = locationClient

    data class Point (
        val latlon: Pair<Double, Double>,
        val name: String,
        var isPassed: Boolean
    )

    suspend fun getProximityFlow(latlon: Pair<Double, Double>): Flow<Double> {
        return location.getProximityFlow(latlon)
    }

    suspend fun getAverageLocation(): Pair<Double, Double>{
        return location.getAverageLocation(6)
    }
}