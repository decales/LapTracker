package cmpt370.group12.laptracker

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class LocationClient (

    private val context: Context,
    private val activity: Activity,
    private val client: FusedLocationProviderClient

) {
    private fun servicesEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }


    private fun hasLocationPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }


    fun getLocationPermission() {
        if (!servicesEnabled()) {
            throw Exception("GPS is not available!")
        }
        if (!hasLocationPermissions()) {
            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
        }
    }


//    // DOES NOT WORK
//    @SuppressLint("MissingPermission")
//    suspend fun getCurrentLocation(scope: CoroutineScope): Location? {
//        return scope.async {
//            client.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, CancellationTokenSource().token)
//        }.await().result
//    }


    @SuppressLint("MissingPermission")
    suspend fun getAverageLocation(flow: Flow<Location?>?, numLocations: Int): Pair<Double, Double> {
        var tLong = 1.0
        var tLat = 1.0
        flow?.take(numLocations)?.collect{ location -> // Collect x locations from flow, then stop flow
            if (location != null) {
                tLong += location.longitude
                tLat += location.latitude
            }
        }
        return Pair(tLong/numLocations, tLat/numLocations)
    }


    @SuppressLint("MissingPermission")
    fun getLocationFlow(intervalInSeconds: Double) : Flow<Location?>? {
        if (hasLocationPermissions()) {
            return callbackFlow {
                val request = LocationRequest.create().setPriority(Priority.PRIORITY_HIGH_ACCURACY).setInterval((intervalInSeconds * 1000).toLong())
                val callback = object : LocationCallback() {
                    override fun onLocationResult(result: LocationResult) {
                        result.lastLocation.let { location ->
                            launch {
                                send(location)
                            }
                        }
                    }
                }
                client.requestLocationUpdates(request, callback, Looper.getMainLooper())
                awaitClose {
                    client.removeLocationUpdates(callback)
                }
            }
        }
        else {
            getLocationPermission()
            // TODO enable GPS screen
            return null
        }
    }


    fun checkProximity(locationFlow: Flow<Location>, targetLocation: Location, scope: CoroutineScope) {
        scope.launch {
            locationFlow.collect { location ->
                if (location == targetLocation) { // TODO replace with proximity calc
                    // TODO location is in proximity, do something
                    cancel() // Stop flow in coroutine
                }
            }
        }
    }
}