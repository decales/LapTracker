package cmpt370.group12.laptracker.model.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import cmpt370.group12.laptracker.model.data.entities.CommentEntity
import kotlinx.coroutines.flow.Flow


// I am going to use a convention for function calls, that when programming in an IDE, I personally
// Think is supperior.  It will group functions by their object. So Helpers will show the functions
// tightly coupled.
// object_functionname


@Dao
interface CommentDao
{
    //Using the Upset instead of Insert. This doubles as Insert/Update
    //If we send it a new mappoint, it will add it to the DB, but if we send it a Comment that
    //exists, it will update the mappoint
    @Upsert
        suspend fun Comment_insert(comment: CommentEntity)
    @Delete()
        suspend fun Comment_delete(comment: CommentEntity)

    //todo: I probably want to return the Comments sorted by timeStamp
    @Query("SELECT * FROM commententity WHERE fromTrackId =:trackId")
        fun Comments_getCommentsFromTrackId(trackId: Int): Flow<List<CommentEntity>>
    @Query("SELECT * FROM commententity")
    suspend fun Comments_getAll(): List<CommentEntity>

    @Query("SELECT * FROM commententity")
    fun Comments_getAllFlow(): Flow<List<CommentEntity>>
}


