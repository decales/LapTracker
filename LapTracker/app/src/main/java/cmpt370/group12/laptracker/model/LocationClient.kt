package cmpt370.group12.laptracker.model

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt


class LocationClient(
    private val activity: Activity,
    private val context: Context = activity.applicationContext,
    private val client: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity),
    var locationFlow: MutableSharedFlow<Location?>? = null,
) {

    fun servicesEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }


    fun hasLocationPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }


    fun getLocationPermission() {
        ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
    }


    @SuppressLint("MissingPermission")
    fun startLocationFlow(intervalInSeconds: Double) {
        if (locationFlow == null) {
            val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, (intervalInSeconds * 1000).toLong())
                .setGranularity(Granularity.GRANULARITY_FINE)
                .setMaxUpdateAgeMillis(0)
                .build()
            val callback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    MainScope().launch {
                        locationFlow?.emit(result.lastLocation)
                        print(result.lastLocation)
                    }
                }
            }
            client.requestLocationUpdates(request, callback, Looper.getMainLooper())
        }
    }

    fun stopLocationFlow() {
        if (locationFlow != null) {
            locationFlow = null
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun getAverageLocation(numLocations: Int): LatLng {
        return if (locationFlow != null) {
            var tLat = 0.0
            var tLng = 0.0
            locationFlow?.take(numLocations)?.collectLatest { location -> // Collect x locations from flow
                if (location != null) {
                    tLat += location.latitude
                    tLng += location.longitude
                }
            }
            LatLng(tLat / numLocations, tLng / numLocations)
        } else LatLng(0.0, 0.0)
    }

    suspend fun getSingleLocation(): Location? {
        var location:Location? = null
        if (locationFlow != null) locationFlow?.take(1)?.collect { location = it }
        return location
    }


//    @SuppressLint("MissingPermission")
//    fun startLocationFlow(intervalInSeconds: Double) {
//        if (hasLocationPermissions()) {
//            callbackFlow {
//                val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, (intervalInSeconds * 1000).toLong())
//                    .setGranularity(Granularity.GRANULARITY_FINE)
//                    .setMaxUpdateAgeMillis(0)
//                    .build()
//                val callback = object : LocationCallback() {
//                    override fun onLocationResult(result: LocationResult) {
//                        result.lastLocation.let { location ->
//                            launch {
//                                send(location)
//                            }
//                        }
//                    }
//                }
//                client.requestLocationUpdates(request, callback, Looper.getMainLooper())
//                awaitClose {
//                    client.removeLocationUpdates(callback)
//                }
//            }
//        }
//        else {
//            getLocationPermission()
//            // TODO show enable GPS screen
//        }
//    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Location? = suspendCancellableCoroutine { continuation ->
        val request = CurrentLocationRequest.Builder()
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .setGranularity(Granularity.GRANULARITY_FINE)
            .setMaxUpdateAgeMillis(0)
            .build()
        client.getCurrentLocation(request, null).addOnSuccessListener { location ->
            continuation.resume(location, null)
        }
    }


    suspend fun getProximityFlow(target: Pair<Double, Double>): Flow<Double>? {
        return if (locationFlow !== null) {
            callbackFlow {
                locationFlow?.collectLatest { location ->
                    val dLat = Math.toRadians(target.first) - Math.toRadians(location!!.latitude)
                    val dLon = Math.toRadians(target.second) - Math.toRadians(location.longitude)
                    val x = sin(dLat / 2).pow(2) + cos(Math.toRadians(location.latitude)) * cos(Math.toRadians(target.first)) * sin(dLon / 2).pow(2)
                    val meters =  BigDecimal.valueOf(2 * atan2(sqrt(x), sqrt(1 - x)) * 6378137).setScale(2, RoundingMode.HALF_UP).toDouble()
                    launch {
                        send(meters)
                    }
                }
            }
        } else null
    }
}


