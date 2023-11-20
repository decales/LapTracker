package cmpt370.group12.laptracker.old

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cmpt370.group12.laptracker.presentation.MapScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Feature7 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MapScreen()
        }
    }
}