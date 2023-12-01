package cmpt370.group12.laptracker.viewmodel

import DaoRepositoryImplementation
import androidx.lifecycle.ViewModel
import cmpt370.group12.laptracker.model.data.mapper.DaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dao: DaoRepository
): ViewModel() {
    // TODO add all data and states values required for SettingsView composable functions
    // TODO (if applicable) retrieve data from database (model)
}

