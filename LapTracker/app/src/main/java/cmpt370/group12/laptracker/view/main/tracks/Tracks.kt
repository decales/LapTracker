package cmpt370.group12.laptracker.view.main.tracks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cmpt370.group12.laptracker.viewmodel.main.TracksViewModel

@Composable
fun TracksView(viewModel: TracksViewModel) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "Tracks")
    }
}