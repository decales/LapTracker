package cmpt370.group12.laptracker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

class SideScrollMain {

    @Composable
    fun SideScroll (){
        LazyRow (
            horizontalArrangement = Arrangement.spacedBy(200.dp),
        ){

            item {
                TimerWidget().TimeWidget()
            }
            item {
                SpeedWidget().SpeedPaceWidget()
            }
            item {
                DistanceWidget().DistanceWidget()
            }
        }
    }

}