package cmpt370.group12.laptracker.model.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity
data class AchievementEntity(
    //id : This is the Primary Key For Database Entry into Table AchievementEntity
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    //name is the Title of the achievement
    val name: String = "",
    //description full description of the achievement
    val description: String = "",
    //achieved is a boolean that is asserted when the goal is met
    val achieved: Boolean = false,
    val iconID: Int = 0,
    val timestamp: Long = 0
)