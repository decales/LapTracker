package cmpt370.group12.laptracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cmpt370.group12.laptracker.ui.composable.MapScreen
import cmpt370.group12.laptracker.ui.composable.me
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            MapScreen()
        }
    }
}








