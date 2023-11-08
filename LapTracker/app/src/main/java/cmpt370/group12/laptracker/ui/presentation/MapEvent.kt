package cmpt370.group12.laptracker.ui.presentation

import com.google.android.gms.maps.model.LatLng
import cmpt370.group12.laptracker.domain.model.MapPoint

sealed class AppEvent {

    //If it doesn't need values passed, the make it an object
    //If it does need values passed, then make it a data class
    //everything should be of Type AppEvent()

    object ToggleMap: AppEvent()

    data class AddPointByMapPoint(val mappoint: MapPoint)
    data class AddPointByLatLng(val latLng: LatLng): AppEvent()
    data class OnMapLongClick(val latLng: LatLng): AppEvent()
    data class OnInfoWindowLongClick(val mappoint: MapPoint): AppEvent()
}