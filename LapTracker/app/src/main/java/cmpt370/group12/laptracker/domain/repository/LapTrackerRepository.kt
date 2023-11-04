package cmpt370.group12.laptracker.domain.repository

import cmpt370.group12.laptracker.domain.model.MapPoint
import kotlinx.coroutines.flow.Flow
interface LapTrackerRepository {

    suspend fun insertMapPoint(mappoint: MapPoint)

    suspend fun deleteMapPoint(mappoint: MapPoint)

    fun getMapPoints(fromTrackId: Int): Flow<List<MapPoint>>



}