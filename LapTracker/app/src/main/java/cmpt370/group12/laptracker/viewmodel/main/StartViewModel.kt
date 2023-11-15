package cmpt370.group12.laptracker.viewmodel.main

import androidx.lifecycle.ViewModel
import cmpt370.group12.laptracker.model.LocationClient

class StartViewModel(locationClient: LocationClient) : ViewModel() {
    // TODO add all data and states values required for SettingsView composable functions
    // TODO (if applicable) retrieve data from database (model)
    var location = locationClient

    suspend fun getAverageLocation(): Pair<Double, Double>{
        return location.getAverageLocation(6)
    }
}