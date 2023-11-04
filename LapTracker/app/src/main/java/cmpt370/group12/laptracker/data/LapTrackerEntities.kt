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

@Entity
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
@Entity
data class AchievementEntity(
    //id : This is the Primary Key For Database Entry into Table AchievementEntity
    @PrimaryKey
    val id: Int? = null,
    //name is the Title of the achievement
    val name: String,
    //description full description of the achievement
    val description: String,
    //achieved is a boolean that is asserted when the goal is met
    val achieved: Boolean,
    //Todo: TimeStamp Might Not be The Correct Type, Must Verify This Later
    val timestamp: Timestamp
)
@Entity
data class TrackEntity(
    //id : This is the Primary Key For Database Entry into Table AchievementEntity
    @PrimaryKey
    val id: Int? = null,
    //name is the Title of the achievement
    val name: String

)

@Entity
data class RunEntity(
    //id : This is the Primary Key For Database Entry into Table RunEntity
    @PrimaryKey
    val id: Int? = null,
    // fromTrackId: This is a reference to the primary Key "id" for the TrackEntity Table
    val fromTrackId: Int,
    // starTime,endTime   these are the timestamps for the start and end of a race
    // all times can be calculated by taking these times and comparing them to
    // RunTimeEntities
    //Todo: TimeStamp Might Not be The Correct Type, Must Verify This Later
    val startTime: Timestamp,
    val endTime: Timestamp
)
@Entity
data class RunTimeEntity(
    //id : This is the Primary Key For Database Entry into Table RunTimeEntity
    @PrimaryKey
    val id: Int? = null,
    // fromRunId: This is a reference to the primary Key "id" for the RunEntity Table
    val fromRunId: Int,
    // fromMapPointId: This is a reference to the primary Key "id" for the MapPointEntity Table
    val fromMapPointId: Int,
    // timestamp: this is the timestamp when this point was reached in the run
    //Todo: TimeStamp Might Not be The Correct Type, Must Verify This Later
    val timestamp: Timestamp
)
