package cmpt370.group12.laptracker.domain.model


import java.sql.Timestamp



data class MapPoint(
    //id : This is the Primary Key For Database Entry into Table MapPointEntity
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


data class Comment(
    //id : This is the Primary Key For Database Entry into Table CommentEntity
    val id: Int? = null,
    // fromTrackId: This is a reference to the primary Key "id" for the TrackEntity Table
    val fromTrackId: Int,
    // Comment is the String of the Comment itself
    val comment: String,
    //Todo: TimeStamp Might Not be The Correct Type, Must Verify This Later
    val timestamp: Timestamp
)

data class Achievement(
    //id : This is the Primary Key For Database Entry into Table AchievementEntity
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

data class Track(
    //id : This is the Primary Key For Database Entry into Table AchievementEntity
    val id: Int? = null,
    //name is the Title of the achievement
    val name: String

)


data class Run(
    //id : This is the Primary Key For Database Entry into Table RunEntity
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

data class RunTimeEntity(
    //id : This is the Primary Key For Database Entry into Table RunTimeEntity
    val id: Int? = null,
    // fromRunId: This is a reference to the primary Key "id" for the RunEntity Table
    val fromRunId: Int,
    // fromMapPointId: This is a reference to the primary Key "id" for the MapPointEntity Table
    val fromMapPointId: Int,
    // timestamp: this is the timestamp when this point was reached in the run
    //Todo: TimeStamp Might Not be The Correct Type, Must Verify This Later
    val timestamp: Timestamp
)