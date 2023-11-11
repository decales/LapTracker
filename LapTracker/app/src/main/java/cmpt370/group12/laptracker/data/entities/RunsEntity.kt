package cmpt370.group12.laptracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity
data class RunsEntity(
    //id : This is the Primary Key For Database Entry into Table RunEntity
    @PrimaryKey
    val id: Int? = null,
    // fromTrackId: This is a reference to the primary Key "id" for the TrackEntity Table
    val fromTrackId: Int,
    // starTime,endTime   these are the timestamps for the start and end of a race
    // all times can be calculated by taking these times and comparing them to
    // RunTimeEntities

    val startTime: Long,
    val endTime: Long
)