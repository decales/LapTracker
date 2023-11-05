package cmpt370.group12.laptracker.domain.model

import java.sql.Timestamp

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