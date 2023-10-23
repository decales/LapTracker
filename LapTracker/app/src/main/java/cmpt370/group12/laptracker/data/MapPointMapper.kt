package cmpt370.group12.laptracker.data

import cmpt370.group12.laptracker.domain.model.MapPoint
fun MapPointEntity.toMapPoint(): MapPoint {
    return MapPoint(
        lat = lat,
        lng = lng,
        id = id,
        mapid = mapid

    )
}

fun MapPoint.toMapPointEntity(): MapPointEntity {
    return MapPointEntity(
        lat = lat,
        lng = lng,
        id = id,
        mapid = mapid
    )
}