package cmpt370.group12.laptracker.data

import cmpt370.group12.laptracker.domain.model.MapPoint
import cmpt370.group12.laptracker.domain.repository.MapPointRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
class MapPointRepositoryImpl(

    private val dao: MapPointDao
): MapPointRepository {

    override suspend fun insertMapPoint(mappoint: MapPoint) {
        dao.insertMapPoint(mappoint.toMapPointEntity())
    }

    override suspend fun deleteMapPoint(mappoint: MapPoint) {
        dao.deleteMapPoint(mappoint.toMapPointEntity())
    }

    override fun getMapPoints(idofmap: Int): Flow<List<MapPoint>> {
        return dao.getMapPoints(idofmap).map { mappoint ->
            mappoint.map { it.toMapPoint() }
        }
    }
}
