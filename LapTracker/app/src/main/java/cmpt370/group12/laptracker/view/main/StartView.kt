package cmpt370.group12.laptracker.view.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cmpt370.group12.laptracker.viewmodel.main.StartViewModel

class StartView(
    private val viewModel: StartViewModel
) {
    /* View:
        - Represent the entire start view
        - Made up of class defined composable functions
        - All data is stored retrieved from class view model */
    @Composable
    fun View() {
        // TODO build view from class defined composable functions.
        // TODO initialize necessary view data in viewmodel/main/StartViewModel.kt. Data is accessed through constructor var 'viewModel'
        // Ex.
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(color = viewModel.color)
        ) {
            Button(onClick = { viewModel.setColor()}) {
            }
        }
    }
}