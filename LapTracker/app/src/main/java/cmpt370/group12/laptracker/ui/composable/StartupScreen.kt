package cmpt370.group12.laptracker.ui.composable

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import cmpt370.group12.laptracker.viewModel.ApplicationViewModel
@Composable
fun StartupScreen(
    viewModel: ApplicationViewModel = viewModel()
) {
    if (viewModel.appstate.value.appMode == 0) {   //0: not in a mode (main menu) //1:FreeStyle //2: Track Run  //3: TrackEditMode
        ModeSelectScreen(viewModel = viewModel)
        if (viewModel.appstate.value.flowCurrentLocationActive) {
            //viewModel.FlowCurrentLocation_Stop() // Don't Need to Run the Location Flow while in the Menu.

        }
    }
    else if (viewModel.appstate.value.appMode == 1){
        viewModel.SetLocationFollow(true) //The FreeStyle Mode will track you
       // viewModel.FlowCurrentLocation_Restart()
        MapScreen(viewModel = viewModel)
        if (viewModel.appstate.value.isRealTimeStatVisible){
            RealTimeStatisticsContainer(viewModel = viewModel)
        }
    }
    else if (viewModel.appstate.value.appMode == 2){
        viewModel.SetLocationFollow(false) //The Run Mode Camera Should Capture whole Track
      //  viewModel.FlowCurrentLocation_Restart()
        MapScreen(viewModel = viewModel)
        if (viewModel.appstate.value.isRealTimeStatVisible){
            RealTimeStatisticsContainer(viewModel = viewModel)
        }
    }
    else if (viewModel.appstate.value.appMode == 3){
        viewModel.SetLocationFollow(false) //Track Edit Mode
        viewModel.FlowCurrentLocation_Restart()
        MapScreen(viewModel = viewModel)
        if (viewModel.appstate.value.isRealTimeStatVisible){
            RealTimeStatisticsContainer(viewModel = viewModel)
        }
    }

}