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
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class LocationClient (
    private val context: Context,
    private val activity: Activity,
    private val client: FusedLocationProviderClient
) {

    private fun servicesEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }


    fun hasLocationPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }


    fun getLocationPermission(): Boolean {
        if (!servicesEnabled()) {
            throw Exception("GPS is not available!")
        }
        if (!hasLocationPermissions()) {
            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
        }
        return hasLocationPermissions()
    }


    // DOES NOT WORK
    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(scope: CoroutineScope): Location? {
        val request = scope.async {
            client.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                CancellationTokenSource().token
            )
        }
        return request.await().result
    }


    @SuppressLint("MissingPermission")
    suspend fun getAverageLocation(flow: Flow<Location?>?, numLocations: Int): Pair<Double, Double> {
        var tLat = 0.0
        var tLon = 0.0
        flow?.take(numLocations)?.collectLatest { location -> // Collect x locations from flow, then stop flow
            if (location != null) {
                tLat += location.latitude
                tLon += location.longitude
            }
        }
        return Pair(tLat/numLocations, tLon/numLocations)
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


//    suspend fun checkProximity(locationFlow: Flow<Location?>?, target: Pair<Double, Double>, context: Context) {
//        locationFlow?.collectLatest {location ->
//            val dLat = Math.toRadians(target.first) - Math.toRadians(location!!.latitude)
//            val dLon = Math.toRadians(target.second) - Math.toRadians(location.longitude)
//            val x = sin(dLat / 2).pow(2) + cos(Math.toRadians(location.latitude)) * cos(Math.toRadians(target.first)) * sin(dLon / 2).pow(2)
//            val meters =  BigDecimal.valueOf(2 * atan2(sqrt(x), sqrt(1 - x)) * 6378137).setScale(3, RoundingMode.HALF_UP).toDouble()
//            Toast.makeText(context, "Target is $meters meters away", Toast.LENGTH_SHORT).show()
//        }
//    }


    suspend fun checkProximityFlow(locationFlow: Flow<Location?>?, target: Pair<Double, Double>): Flow<Double> {
        return callbackFlow {
            locationFlow?.collectLatest {location ->
                val dLat = Math.toRadians(target.first) - Math.toRadians(location!!.latitude)
                val dLon = Math.toRadians(target.second) - Math.toRadians(location.longitude)
                val x = sin(dLat / 2).pow(2) + cos(Math.toRadians(location.latitude)) * cos(Math.toRadians(target.first)) * sin(dLon / 2).pow(2)
                val meters =  BigDecimal.valueOf(2 * atan2(sqrt(x), sqrt(1 - x)) * 6378137).setScale(3, RoundingMode.HALF_UP).toDouble()
                launch {
                    send(meters)
                }
            }
        }
    }
}