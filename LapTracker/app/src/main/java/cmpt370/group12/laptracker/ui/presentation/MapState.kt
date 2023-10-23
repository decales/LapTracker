package cmpt370.group12.laptracker.ui.presentation
import com.google.maps.android.compose.MapProperties
import cmpt370.group12.laptracker.domain.model.MapPoint
data class MapState(
    val properties: MapProperties = MapProperties(),
    val mapPoints: List<MapPoint> = emptyList(),

)
