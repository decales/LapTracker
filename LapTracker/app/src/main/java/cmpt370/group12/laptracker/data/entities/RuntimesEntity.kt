package cmpt370.group12.laptracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp


@Entity
data class RunTimesEntity(
    //id : This is the Primary Key For Database Entry into Table RunTimeEntity
    @PrimaryKey
    val id: Int? = null,
    // fromRunId: This is a reference to the primary Key "id" for the RunEntity Table
    val fromRunId: Int,
    // fromMapPointId: This is a reference to the primary Key "id" for the MapPointEntity Table
    val fromMapPointId: Int,
    // timestamp: this is the timestamp when this point was reached in the run

    val timestamp: Long
)