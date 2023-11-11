package cmpt370.group12.laptracker.viewmodel.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class StartViewModel : ViewModel() {
    // TODO add all data and states values required for SettingsView composable functions
    // TODO (if applicable) retrieve data from database (model)

    // Ex.
    var color by mutableStateOf(Color.Gray)
        private set

    fun setColor() {
        if (color == Color.Gray) color = Color.Black else color = Color.Gray
    }
}