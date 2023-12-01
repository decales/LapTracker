package cmpt370.group12.laptracker.model.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class Track(
    val id: Int?,
    val name: String,
    val location: String,
    val comment: String,
    val mapImage: Int,
    val points: List<Pair<Double, Double>>, // latitude, longitude
    var lapTimes: List<Pair<Long, Long>> // start time, finish time
)