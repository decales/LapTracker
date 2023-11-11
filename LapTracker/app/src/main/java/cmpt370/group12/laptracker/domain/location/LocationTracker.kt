package cmpt370.group12.laptracker.domain.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationTracker {
    fun currentLocationFlow(intervalInMilliSeconds: Long) : Flow<Location?>
}