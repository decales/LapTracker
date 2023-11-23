package cmpt370.group12.laptracker.viewmodel

import androidx.lifecycle.ViewModel
import cmpt370.group12.laptracker.model.domain.repository.LapTrackerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val db: LapTrackerRepository
): ViewModel() {
    // TODO add all data and states values required for SettingsView composable functions
    // TODO (if applicable) retrieve data from database (model)
}

