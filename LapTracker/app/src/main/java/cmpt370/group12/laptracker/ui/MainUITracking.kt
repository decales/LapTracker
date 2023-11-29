package cmpt370.group12.laptracker.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmpt370.group12.laptracker.old.DisplaySelectedWidgets
import cmpt370.group12.laptracker.view.theme.LapTrackerTheme

class MainUITracking : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LapTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                ) {

                    RealTimeUI()

                }
            }
        }
    }
}

    /*
    The main app composable where everything is added together
    the oncreate function will call this to compose the application
     */
    @Composable
    fun RealTimeUI () {

        var isRunning by remember {  mutableStateOf (false)}
        val selectedViewModel = viewModel  <SelectedItemsViewModel>()
        //val stateClass = MapState()

        Column (

                modifier = Modifier.fillMaxSize()
        ){
            //DropdownMenu ( selectedViewModel)
            UIDropdown().DropdownMenu (selectedViewModel)
            Spacer ( modifier = Modifier.height(16.dp))

            DisplaySelectedWidgets ( selectedViewModel)

            Spacer ( modifier = Modifier.height(16.dp))

            /*
            BottomButton(
                    isRunning = isRunning,

                    onStartClick = { isRunning = true},
                    onStopClick = { isRunning = false},
                    onResumeClick = { isRunning = true}
            )

             */
        }
    }
