package cmpt370.group12.laptracker.model.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cmpt370.group12.laptracker.model.data.entities.RunTimesEntity
import kotlinx.coroutines.flow.Flow

// I am going to use a convention for function calls, that when programming in an IDE, I personally
// Think is supperior.  It will group functions by their object. So Helpers will show the functions
// tightly coupled.
// object_functionname

@Dao
interface RuntimesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun RunTimes_insert(runtimes: RunTimesEntity) :Long
    @Delete
    suspend fun RunTimes_delete(runtimes: RunTimesEntity)

    @Query("SELECT * FROM runtimesentity WHERE id =:runId")
    fun RunTimes_getByRunId(runId: Int): Flow<List<RunTimesEntity>>
    @Query("SELECT * FROM runtimesentity")
    fun RunTimes_getAll(): List<RunTimesEntity>

    @Query("SELECT * FROM runtimesentity")
    fun RunTimes_getAllFlow(): Flow<List<RunTimesEntity>>

}


