package cmpt370.group12.laptracker.domain.model

data class MapPoint(
    val lat: Double,
    val lng: Double,
    val id: Int? = null,
    val mapid: Int = 0

)