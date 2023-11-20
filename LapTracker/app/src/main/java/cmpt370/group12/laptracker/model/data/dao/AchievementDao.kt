package cmpt370.group12.laptracker.model.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import cmpt370.group12.laptracker.model.data.entities.AchievementEntity
import kotlinx.coroutines.flow.Flow


// I am going to use a convention for function calls, that when programming in an IDE, I personally
// Think is supperior.  It will group functions by their object. So Helpers will show the functions
// tightly coupled.
// object_functionname

@Dao
interface AchievementDao {
    @Upsert
    suspend fun Achievement_insert(spot: AchievementEntity)

    @Delete
    suspend fun Achievement_delete(spot: AchievementEntity)

    @Query("SELECT * FROM achievemententity WHERE achieved =1")
    fun Achievement_getFinished(): Flow<List<AchievementEntity>>

    @Query("SELECT * FROM achievemententity WHERE achieved =0")
    fun Achievement_getUnFinished(): Flow<List<AchievementEntity>>

    @Query("SELECT * FROM achievemententity")
    fun Achievement_getAll(): Flow<List<AchievementEntity>>

    @Query("SELECT * FROM achievemententity")
    suspend fun Achievement_getAll2(): List<AchievementEntity>
}
