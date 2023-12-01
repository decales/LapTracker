package cmpt370.group12.laptracker.ui

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable

class SideScrollMain {

    @Composable
    fun SideScroll (){
        LazyRow {
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