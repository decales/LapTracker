package cmpt370.group12.laptracker.model.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MapPointEntity(
    //id : This is the Primary Key For Database Entry into Table MapPointEntity
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    // fromTrackId: This is a reference to the primary Key "id" for the TrackEntity Table
    val fromTrackId: Int = 999,
    // latitude and longitude points for the Point Being Saved
    val latitude: Double = 0.0,
    val longitude: Double =0.0,
    // (Future Use: name of the point or ""
    val name: String = "",
    // sequenceNumber: The Order of the Point in a Track, This Will Later Be Used For Sorting Points
    // on the track
    val sequenceNumber: Int = 0
)