package cmpt370.group12.laptracker.domain.model

import java.sql.Timestamp

data class Runs(
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