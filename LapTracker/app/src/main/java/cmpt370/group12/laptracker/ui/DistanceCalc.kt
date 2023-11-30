package cmpt370.group12.laptracker.ui

import android.location.Location

class DistanceCalc {
    private var totalDistance: Float = 0f
    private var prevLocation: Location? = null
    fun update (current: Location?){
        if (prevLocation != null){
            val change = current?.let { prevLocation!!.distanceTo(it) }
            if (change != null) {
                totalDistance += change
            }
        }
        prevLocation = current
    }
    fun resetTracking(){
        totalDistance = 0f
    }
    fun getDistance(): Float {
        return totalDistance
    }
}