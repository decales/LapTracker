package cmpt370.group12.laptracker

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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


    private fun getLocationPermission() {
        if (!servicesEnabled()) {
            throw Exception("GPS is not available!")
        }
        if (!hasLocationPermissions()) {
            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
        }
    }


    @SuppressLint("MissingPermission")
    fun requestCurrentLocation() {
        if (hasLocationPermissions()) {
            val request = LocationRequest.create().setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            val callback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    result.lastLocation.let { location ->
                        if (location != null) {
                            Log.d(null, result.lastLocation.toString())
                        }
                    }
                }
            }
            client.requestLocationUpdates(request, callback, Looper.getMainLooper())
        }
        else {
            getLocationPermission()
        }
    }


    @SuppressLint("MissingPermission")
    fun getLocationFlow(intervalInSeconds: Long) : Flow<Location?>? {
        if (hasLocationPermissions()) {
            return callbackFlow {
                val request = LocationRequest.create().setPriority(Priority.PRIORITY_HIGH_ACCURACY).setInterval(intervalInSeconds * 1000)
                val callback = object : LocationCallback() {
                    override fun onLocationResult(result: LocationResult) {
                        //Log.d(null, result.lastLocation.toString())
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
}