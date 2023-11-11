package cmpt370.group12.laptracker.Testing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmpt370.group12.laptracker.domain.model.MapPoint
import cmpt370.group12.laptracker.domain.repository.LapTrackerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestDatabaseViewModel @Inject constructor(
    val repository: LapTrackerRepository
): ViewModel() {
    var isemptyDB: Boolean = true
    var isPassed: Boolean = false
    fun Test_InsertMapPoint(expected: MapPoint) {
        println("TESTING Insert MapPoint")
        var mapPoint: MapPoint
        viewModelScope.launch {
            repository.MapPoint_insert(expected)
            mapPoint = repository.MapPoints_getAll().last()
                if ((mapPoint.fromTrackId == expected.fromTrackId) && (mapPoint.latitude == expected.latitude)
                    && (mapPoint.longitude == mapPoint.longitude) && (mapPoint.name == expected.name)
                    && (mapPoint.sequenceNumber == expected.sequenceNumber)
                    ) {
                        println("TESTING Test Passed")
                    } else {
                        println("TESTING Test Failed")

                    }
                }


        }

    fun Test_DeleteMapPoint(expected: MapPoint) {
        println("TESTING Insert MapPoint")
        var mapPoint: MapPoint
        viewModelScope.launch {
            repository.MapPoint_delete(expected)
            mapPoint = repository.MapPoints_getAll().last()
               if ((mapPoint.fromTrackId == expected.fromTrackId) && (mapPoint.latitude == expected.latitude)
                    && (mapPoint.longitude == mapPoint.longitude) && (mapPoint.name == expected.name)
                    && (mapPoint.sequenceNumber == expected.sequenceNumber)
                    ) {
                        println("TESTING Test Passed")
                    } else {
                        println("TESTING Test Failed")

                    }
        }


    }
}







