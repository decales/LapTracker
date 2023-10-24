package cmpt370.group12.laptracker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

class Achievements {

    @Composable
    fun updateAchievement(achievementStatus: SnapshotStateMap<String, Boolean>, currentAchievement: String) {
        achievementStatus[currentAchievement] = true
    }
    @Composable
    fun ShowAchievement(achievementName: String, update: MutableState<Boolean>) {
        //popup that shows that achievement was gotten
        AnimatedVisibility(
            visible = update.value, enter = slideInVertically(),
            exit = slideOutVertically()
        ) {
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .size(1000.dp, 50.dp)
                    .fillMaxSize().background(Color.Green)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Achievement: $achievementName")
                }
            }
            LaunchedEffect(Unit) {
                delay(2000)
                update.value = false
            }
        }
    }
}