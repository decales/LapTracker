package cmpt370.group12.laptracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cmpt370.group12.laptracker.ui.composable.StartupScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StartupScreen()
            //ModeSelectScreen()
            //MapScreen()
        }
    }
}









