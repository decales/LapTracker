package cmpt370.group12.laptracker.domain.model

import java.sql.Timestamp

data class Runtimes(
    //id : This is the Primary Key For Database Entry into Table RunTimeEntity
    val id: Int? = null,
    // fromRunId: This is a reference to the primary Key "id" for the RunEntity Table
    val fromRunId: Int,
    // fromMapPointId: This is a reference to the primary Key "id" for the MapPointEntity Table
    val fromMapPointId: Int,
    // timestamp: this is the timestamp when this point was reached in the run
    val timestamp: Long
)