package cmpt370.group12.laptracker

import androidx.compose.runtime.snapshots.SnapshotStateMap

class Achievements {
    fun updateAchievement(achievementStatus: SnapshotStateMap<String, Boolean>, currentAchievement: String) {
        achievementStatus[currentAchievement] = true
    }
}