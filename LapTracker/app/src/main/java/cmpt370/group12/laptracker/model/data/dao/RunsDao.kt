package cmpt370.group12.laptracker.model.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cmpt370.group12.laptracker.model.data.entities.RunsEntity
import kotlinx.coroutines.flow.Flow

// I am going to use a convention for function calls, that when programming in an IDE, I personally
// Think is supperior.  It will group functions by their object. So Helpers will show the functions
// tightly coupled.
// object_functionname

@Dao
interface RunsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun Runs_insert(runs: RunsEntity):Long

    @Delete
    suspend fun Runs_delete(runs: RunsEntity)

    @Query("SELECT * FROM runsentity WHERE fromTrackId =:trackId")
    suspend fun Runs_getByTrackId(trackId: Int): List<RunsEntity>

    @Query("SELECT * FROM runsentity WHERE fromTrackId =:trackId")
    fun Runs_getByTrackIdFlow(trackId: Int): Flow<List<RunsEntity>>

    @Query("SELECT * FROM runsentity")
    fun Runs_getAllFlow(): Flow<List<RunsEntity>>

    @Query("SELECT * FROM runsentity")
    suspend fun Runs_getAll(): List<RunsEntity>
}

