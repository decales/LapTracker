package cmpt370.group12.laptracker.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MapPointDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMapPoint(spot: MapPointEntity)

    @Delete
    suspend fun deleteMapPoint(spot: MapPointEntity)

    @Query("SELECT * FROM mappointentity WHERE mapid =:idofmap")
    fun getMapPoints(idofmap: Int): Flow<List<MapPointEntity>>
}
