package cmpt370.group12.laptracker.data

import cmpt370.group12.laptracker.domain.model.MapPoint
import cmpt370.group12.laptracker.domain.repository.LapTrackerRepository
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.map
class LapTrackerRepositoryImpl(

    private val dao: LapTrackerDao
): LapTrackerRepository {

    override suspend fun insertMapPoint(mappoint: MapPoint) {
        dao.insertMapPoint(mappoint.toMapPointEntity())
    }

    override suspend fun deleteMapPoint(mappoint: MapPoint) {
        dao.deleteMapPoint(mappoint.toMapPointEntity())
    }

    override fun getMapPoints(fromTrackId: Int): Flow<List<MapPoint>> {
        return dao.getMapPoints(fromTrackId).map { mappoint ->
            mappoint.map { it.toMapPoint() }
        }
    }
}
