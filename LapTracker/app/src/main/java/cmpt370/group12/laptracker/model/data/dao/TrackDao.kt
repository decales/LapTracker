package cmpt370.group12.laptracker.model.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cmpt370.group12.laptracker.model.data.entities.TrackEntity
import kotlinx.coroutines.flow.Flow

// I am going to use a convention for function calls, that when programming in an IDE, I personally
// Think is supperior.  It will group functions by their object. So Helpers will show the functions
// tightly coupled.
// object_functionname

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun Track_insert(track: TrackEntity): Long
    @Delete
    suspend fun Track_delete(track: TrackEntity)

    @Query("SELECT * FROM trackentity WHERE id =:id")
    fun Track_getById(id: Int): Flow<List<TrackEntity>>

    @Query("SELECT * FROM trackentity")
    fun Track_getAllFlow(): Flow<List<TrackEntity>>

    @Query("SELECT * FROM trackentity")
    suspend fun Track_getAll(): List<TrackEntity>
}


