package cmpt370.group12.laptracker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class Achievements {

    @Composable
    fun updateAchievement(achievementStatus: SnapshotStateMap<String, Boolean>, currentAchievement: String) {
        if (achievementStatus[currentAchievement] == false) {
            ShowAchievement(currentAchievement)
        }
        achievementStatus[currentAchievement] = true
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun ShowAchievement(achievementName: String) {
        //popup that shows that achievement was gotten
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .size(300.dp, 100.dp)
                    .animateEnterExit(
                        enter = slideInVertically(),
                        exit = slideOutVertically()
                    )
                    .fillMaxSize().background(Color.Green)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = achievementName)
                }
            }
        }
    }
}