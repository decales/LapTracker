package cmpt370.group12.laptracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity
data class MapPointEntity(
    //id : This is the Primary Key For Database Entry into Table MapPointEntity
    @PrimaryKey
    val id: Int? = null,
    // fromTrackId: This is a reference to the primary Key "id" for the TrackEntity Table
    val fromTrackId: Int,
    // latitude and longitude points for the Point Being Saved
    val latitude: Double,
    val longitude: Double,
    // (Future Use: name of the point or ""
    val name: String = "",
    // sequenceNumber: The Order of the Point in a Track, This Will Later Be Used For Sorting Points
    // on the track
    val sequenceNumber: Int
)
data class CommentEntity(
    //id : This is the Primary Key For Database Entry into Table CommentEntity
    @PrimaryKey
    val id: Int? = null,
    // fromTrackId: This is a reference to the primary Key "id" for the TrackEntity Table
    val fromTrackId: Int,
    // Comment is the String of the Comment itself
    val comment: String,
    //Todo: TimeStamp Might Not be The Correct Type, Must Verify This Later
    val timestamp: Timestamp
)