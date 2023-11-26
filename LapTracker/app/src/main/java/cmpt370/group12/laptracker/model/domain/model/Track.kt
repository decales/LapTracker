package cmpt370.group12.laptracker.model.domain.model

data class Track(
    //id : This is the Primary Key For Database Entry into Table AchievementEntity
    val id: Int? = null,
    //name is the Title of the achievement
    val name: String,
    val location: String,
    val mapImage: Int
)