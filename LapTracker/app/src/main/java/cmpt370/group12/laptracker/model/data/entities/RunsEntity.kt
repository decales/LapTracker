package cmpt370.group12.laptracker.model.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity
data class RunsEntity(
    //id : This is the Primary Key For Database Entry into Table RunEntity
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    // fromTrackId: This is a reference to the primary Key "id" for the TrackEntity Table
    val fromTrackId: Int = 0,
    // starTime,endTime   these are the timestamps for the start and end of a race
    // all times can be calculated by taking these times and comparing them to
    // RunTimeEntities

    val startTime: Long = 0,
    val endTime: Long = 0
)