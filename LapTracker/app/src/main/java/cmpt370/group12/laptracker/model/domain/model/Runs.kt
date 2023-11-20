package cmpt370.group12.laptracker.model.domain.model

data class Runs(
    //id : This is the Primary Key For Database Entry into Table RunEntity
    val id: Int? = null,
    // fromTrackId: This is a reference to the primary Key "id" for the TrackEntity Table
    val fromTrackId: Int,
    // starTime,endTime   these are the timestamps for the start and end of a race
    // all times can be calculated by taking these times and comparing them to
    // RunTimeEntities

    val startTime: Long,
    val endTime: Long
)