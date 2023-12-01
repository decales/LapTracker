package cmpt370.group12.laptracker.model.domain.model

import com.google.android.gms.maps.model.LatLng

data class Track(
    //id : This is the Primary Key For Database Entry into Table AchievementEntity
    val id: Int?,
    //name is the Title of the achievement
    val name: String,
    val location: String,
    val comment: String,
    val mapImage: Int,
    val points: List<LatLng>
)