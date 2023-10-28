package cmpt370.group12.laptracker.ui.presentation

import com.google.android.gms.maps.model.LatLng
import cmpt370.group12.laptracker.domain.model.MapPoint

sealed class MapEvent {
    object ToggleMap: MapEvent()
    data class OnMapLongClick(val latLng: LatLng): MapEvent()
    data class OnInfoWindowLongClick(val mappoint: MapPoint): MapEvent()
}