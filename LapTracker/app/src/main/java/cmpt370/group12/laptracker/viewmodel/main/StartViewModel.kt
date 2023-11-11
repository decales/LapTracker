package cmpt370.group12.laptracker.viewmodel.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class StartViewModel : ViewModel() {

    var color by mutableStateOf(Color.Red)
        private set

    fun setColor() {
        if (color == Color.Red) color = Color.Blue else color = Color.Red
    }
}