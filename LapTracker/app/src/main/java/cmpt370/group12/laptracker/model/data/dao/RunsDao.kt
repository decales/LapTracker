package cmpt370.group12.laptracker.model.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import cmpt370.group12.laptracker.model.data.entities.RunsEntity
import kotlinx.coroutines.flow.Flow

// I am going to use a convention for function calls, that when programming in an IDE, I personally
// Think is supperior.  It will group functions by their object. So Helpers will show the functions
// tightly coupled.
// object_functionname

@Dao
interface RunsDao {
    @Upsert
    suspend fun Runs_insert(runs: RunsEntity)

    @Delete
    suspend fun Runs_delete(runs: RunsEntity)

    @Query("SELECT * FROM runsentity WHERE fromTrackId =:trackId")
    suspend fun Runs_getByTrackId(trackId: Int): List<RunsEntity>

    @Query("SELECT * FROM runsentity")
    fun Runs_getAllFlow(): Flow<List<RunsEntity>>

    @Query("SELECT * FROM runsentity")
    fun Runs_getAll(): List<RunsEntity>
}

