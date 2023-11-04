package cmpt370.group12.laptracker.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LapTrackerDao {

    //Comment Table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(spot: CommentEntity)
    @Delete
    suspend fun deleteComment(spot: CommentEntity)

    //todo: I probably want to return the Comments sorted by timeStamp
    @Query("SELECT * FROM commententity WHERE fromTrackId =:currentTrackId")
    fun getComments(currentTrackId: Int): Flow<List<CommentEntity>>

    //MapPoint Table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMapPoint(spot: MapPointEntity)
    @Delete
    suspend fun deleteMapPoint(spot: MapPointEntity)

    //todo: I probably want to return the map points sorted by sequenceNumber
    @Query("SELECT * FROM mappointentity WHERE fromTrackId =:currentTrackId")
    fun getMapPoints(currentTrackId: Int): Flow<List<MapPointEntity>>

    //Achievement Table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievement(spot: AchievementEntity)
    @Delete
    suspend fun deleteAchievement(spot: AchievementEntity)
    @Update
    suspend fun setAchievement(spot: AchievementEntity)
    @Query("SELECT * FROM achievemententity WHERE achieved =1")
    fun getfinishedAchievements(): Flow<List<AchievementEntity>>
    @Query("SELECT * FROM achievemententity WHERE achieved =0")
    fun getunfinishedAchievements(): Flow<List<AchievementEntity>>
    @Query("SELECT * FROM achievemententity")
    fun getallAchievements(): Flow<List<AchievementEntity>>

    //Run Table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRun(spot: RunEntity)
    @Delete
    suspend fun deleteRun(spot: RunEntity)

    @Query("SELECT * FROM runentity WHERE fromTrackId =:currentTrackId")
    fun getRuns(currentTrackId: Int): Flow<List<RunEntity>>

}
