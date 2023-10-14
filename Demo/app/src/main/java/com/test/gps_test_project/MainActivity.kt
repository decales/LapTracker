package com.test.gps_test_project

import android.Manifest
import android.location.Location
import android.location.LocationManager
import android.location.LocationListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.content.Context
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnSuccessListener

class MainActivity : AppCompatActivity() {
    lateinit var longitude: TextView
    lateinit var latitude: TextView
    var buttonClicks = 0
    lateinit var clicks: TextView
    lateinit var locationManager: LocationManager
    val locationPermissionCode = 2

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    var priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

    val cancelToken = CancellationTokenSource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Tracking App"
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        val getLocationButton: Button = findViewById(R.id.getLocation)
        getLocationButton.setOnClickListener {
            getLocation()
            buttonClicks += 1
            clicks = findViewById(R.id.clicks)
            clicks.text = "$buttonClicks"
        }
    }

    fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }

        fusedLocationProviderClient.getCurrentLocation(priority, cancelToken.token)
            .addOnSuccessListener {updateUIValues(it)}
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                getLocation()
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    fun updateUIValues(location: Location) {
        longitude = findViewById(R.id.longitude)
        latitude = findViewById(R.id.latitude)
        latitude.text = "Latitude" + location.latitude.toString()
        longitude.text = "Longitude" + location.longitude.toString()
    }

}
