package cmpt370.group12.laptracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrackEntity(
    //id : This is the Primary Key For Database Entry into Table AchievementEntity
    @PrimaryKey
    val id: Int? = null,
    //name is the Title of the achievement
    val name: String

)