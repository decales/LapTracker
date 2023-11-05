package cmpt370.group12.laptracker.domain.model

import java.sql.Timestamp

data class Achievement(
    //id : This is the Primary Key For Database Entry into Table AchievementEntity
    val id: Int? = null,
    //name is the Title of the achievement
    val name: String,
    //description full description of the achievement
    val description: String,
    //achieved is a boolean that is asserted when the goal is met
    val achieved: Boolean,

    val timestamp: Long
)