package cmpt370.group12.laptracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity
data class CommentEntity(
    //id : This is the Primary Key For Database Entry into Table CommentEntity
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    // fromTrackId: This is a reference to the primary Key "id" for the TrackEntity Table
    val fromTrackId: Int = 0,
    // Comment is the String of the Comment itself
    val comment: String = "",

    val timestamp: Long = 0
)