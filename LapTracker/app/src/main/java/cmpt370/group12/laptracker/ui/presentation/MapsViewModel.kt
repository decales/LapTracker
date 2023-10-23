package cmpt370.group12.laptracker.ui.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.MapStyleOptions
import cmpt370.group12.laptracker.domain.model.MapPoint
import cmpt370.group12.laptracker.domain.repository.MapPointRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val repository: MapPointRepository
): ViewModel() {
//class MapsViewModel: ViewModel(){
    var state by mutableStateOf(MapState())
    var usemap = 0
init{
    refreshMapPoints()
}

fun refreshMapPoints(){
    viewModelScope.launch {
        repository.getMapPoints(usemap).collectLatest { mappoints ->
            state = state.copy(
                mapPoints = mappoints
            )
        }
    }

}
    fun change_map(idofmap: Int){
        usemap=idofmap
        refreshMapPoints()
    }

    fun onEvent(event: MapEvent) {
        when(event) {
            is MapEvent.ToggleMap -> {
                if (usemap ==0) {change_map(1)}
                else {change_map(0)}

            }
            is MapEvent.OnMapLongClick -> {
                viewModelScope.launch {
                    repository.insertMapPoint(
                        MapPoint(
                        event.latLng.latitude,
                        event.latLng.longitude
                    )
                    )
                }
            }
            is MapEvent.OnInfoWindowLongClick -> {
                viewModelScope.launch {
                    repository.deleteMapPoint(event.mappoint)
                }
            }
        }
    }
}