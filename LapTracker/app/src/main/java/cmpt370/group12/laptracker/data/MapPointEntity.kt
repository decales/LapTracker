package cmpt370.group12.laptracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MapPointEntity(
    val lat: Double,
    val lng: Double,
    @PrimaryKey val id: Int? = null,
    val mapid: Int

)
