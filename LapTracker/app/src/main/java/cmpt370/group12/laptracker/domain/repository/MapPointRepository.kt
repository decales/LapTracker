package cmpt370.group12.laptracker.domain.repository

import cmpt370.group12.laptracker.domain.model.MapPoint
import kotlinx.coroutines.flow.Flow
interface MapPointRepository {

    suspend fun insertMapPoint(mappoint: MapPoint)

    suspend fun deleteMapPoint(mappoint: MapPoint)

    fun getMapPoints(idofmap: Int): Flow<List<MapPoint>>
}