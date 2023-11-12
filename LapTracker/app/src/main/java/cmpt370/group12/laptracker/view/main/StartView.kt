package cmpt370.group12.laptracker.view.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        Header()
    }

    @Composable
    fun Header() {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp)
        )
        {
            Text(
                text = "Start Tracking",
                fontSize = 30.sp
            )
        }
    }
}