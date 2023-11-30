package cmpt370.group12.laptracker.model.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrackEntity(
    //id : This is the Primary Key For Database Entry into Table AchievementEntity
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    //name is the Title of the achievement
    val name: String = "",
    val location: String = "",
    val comment: String = "",
    val mapImage: Int = 0
)