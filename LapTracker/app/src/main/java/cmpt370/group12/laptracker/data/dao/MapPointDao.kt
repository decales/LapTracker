package cmpt370.group12.laptracker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import cmpt370.group12.laptracker.data.entities.MapPointEntity
import kotlinx.coroutines.flow.Flow

// I am going to use a convention for function calls, that when programming in an IDE, I personally
// Think is supperior.  It will group functions by their object. So Helpers will show the functions
// tightly coupled.
// object_functionname

@Dao
interface MapPointDao {

    @Upsert
    suspend fun MapPoint_insert(mapPoint: MapPointEntity)

    @Delete
    suspend fun MapPoint_delete(mapPoint: MapPointEntity)

    //todo: I probably want to return the map points sorted by sequenceNumber
    @Query("SELECT * FROM mappointentity WHERE fromTrackId =:trackId")
    fun MapPoints_getPointsByTrackId(trackId: Int): Flow<List<MapPointEntity>>

    //this will just pull all the mappoints from the entire database
    //most like to be used for testing
    @Query("SELECT * FROM mappointentity")
    suspend fun MapPoints_getAll(): List<MapPointEntity>

    @Query("SELECT * FROM mappointentity")
    fun MapPoints_getAllFlow(): Flow<List<MapPointEntity>>
}