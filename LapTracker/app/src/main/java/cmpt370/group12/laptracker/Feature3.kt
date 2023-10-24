package cmpt370.group12.laptracker
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cmpt370.group12.laptracker.ui.theme.LapTrackerTheme

class Feature3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val achievements = Achievements()
        val achievementStatus = mutableStateMapOf("CreateFirstTrack" to false)
        setContent {
            LapTrackerTheme {
                AchievementsScreen(achievements, achievementStatus)
            }
        }
    }
}


@Composable
fun AchievementsScreen(achievements: Achievements, achievementStatusInit: SnapshotStateMap<String, Boolean>) {
    //Variable that store whether the achievements have been met
    val achievementStatus = remember { achievementStatusInit }
    val update = remember { mutableStateOf(false)}
    //initial values would need to be loaded from database
    Box (
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            achievements.ShowAchievement("CreateFirstTrack", update)
            if (update.value){
                achievements.updateAchievement(achievementStatus, "CreateFirstTrack")
            }
        }
    }
    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box( // Container to display achievements
                modifier = Modifier
                    .padding(bottom = 100.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    achievementStatus.forEach { achievement ->
                        Text(text = achievement.key + ": " + achievement.value.toString())
                    }
                }
            }
                ActivateAchievement(update)
        }
    }
}

@Composable
fun ActivateAchievement(update: MutableState<Boolean>) {
    Button( // Activate the achievement
        onClick = {
            update.value = !update.value
        }
    ) {
        Text("Activate Achievement")
    }
}