package cmpt370.group12.laptracker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import cmpt370.group12.laptracker.data.entities.TrackEntity
import kotlinx.coroutines.flow.Flow

// I am going to use a convention for function calls, that when programming in an IDE, I personally
// Think is supperior.  It will group functions by their object. So Helpers will show the functions
// tightly coupled.
// object_functionname

@Dao
interface TrackDao {

    @Upsert
    suspend fun Track_insert(track: TrackEntity)
    @Delete
    suspend fun Track_delete(track: TrackEntity)

    @Query("SELECT * FROM trackentity WHERE id =:id")
    fun Track_getById(id: Int): Flow<List<TrackEntity>>

    @Query("SELECT * FROM trackentity")
    fun Track_getAllFlow(): Flow<List<TrackEntity>>

    @Query("SELECT * FROM trackentity")
    fun Track_getAll(): List<TrackEntity>
}


