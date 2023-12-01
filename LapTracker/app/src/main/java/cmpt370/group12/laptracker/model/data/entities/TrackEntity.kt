package cmpt370.group12.laptracker.model.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import kotlinx.serialization.Serializable

@Entity
data class TrackEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String = "",
    val location: String = "",
    val comment: String = "",
    val mapImage: Int = 0,
    val points: String = "",
    val lapTimes: String = ""
)