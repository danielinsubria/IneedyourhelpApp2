package com.example.ineedyourhelpapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.ineedyourhelpapp.R

class MainActivity3 : AppCompatActivity(), LocationListener {
    private lateinit var locationManager: LocationManager
    private lateinit var tvGpsLocation: TextView
    private val locationPermissionCode = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        title = "KotlinApp"
        val button: Button = findViewById(R.id.getLocation)
        button.setOnClickListener {
            getLocation()
        }
    }
    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }
    override fun onLocationChanged(location: Location) {
        tvGpsLocation = findViewById(R.id.textView)
        tvGpsLocation.text = "Latitude: " + location.latitude + " , Longitude: " + location.longitude
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}























































/*package com.example.ineedyourhelpapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.Manifest
import android.annotation.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.support.*
import android.os.*
import android.provider.Settings
import android.util.Log
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main3.*
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

private const val PERMISSION_REQUEST = 10

class MainActivity3 : AppCompatActivity() {

    lateinit var locationManager: LocationManager
    private var hasGps = false
    private var hasNetwork = false
    private var locationGps: Location? = null
    private var locationNetwork: Location? = null
    private var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        disableView()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission(permissions)) {

                enableView()
            }else {

                requestPermissions(permissions, PERMISSION_REQUEST)
            }
        }else {

            enableView()
        }
    }
    private fun disableView() {

        btn_get_location.isEnabled = false
        btn_get_location.alpha = 0.5F
    }
    private fun enableView() {
        btn_get_location.isEnabled = true
        btn_get_location.alpha = 1F
        btn_get_location.setOnClickListener { getLocation() }
        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
    }
    @SuppressLint("MissingPermission")
    private fun getLocation() {

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (hasGps || hasNetwork) {

            if (hasGps) {

                Log.d("CodeAndroidLocation", "hasGps")
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0F, object : LocationListener {
                    override fun onLocationChanged(location: Location) {

                        if (location != null) {

                            locationGps = location
                            tv_result.append("\nGPS ")
                            tv_result.append("\nLatitude : " + locationGps!!.latitude)
                            tv_result.append("\nLongitude : " + locationGps!!.longitude)
                            Log.d("CodeAndroidLocation", " GPS Latitude : " + locationGps!!.latitude)
                            Log.d("CodeAndroidLocation", " GPS Longitude : " + locationGps!!.longitude)
                        }
                    }
                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

                    }
                    override fun onProviderEnabled(provider: String) {

                    }
                    override fun onProviderDisabled(provider: String) {

                    }
                })
                val localGpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (localGpsLocation != null)
                    locationGps = localGpsLocation
            }
            if (hasNetwork) {

                Log.d("CodeAndroidLocation", "hasGps")
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0F, object : LocationListener {
                    override fun onLocationChanged(location: Location) {

                        if (location != null) {

                            locationNetwork = location
                            tv_result.append("\nNetwork ")
                            tv_result.append("\nLatitude : " + locationNetwork!!.latitude)
                            tv_result.append("\nLongitude : " + locationNetwork!!.longitude)
                            Log.d("CodeAndroidLocation", " Network Latitude : " + locationNetwork!!.latitude)
                            Log.d("CodeAndroidLocation", " Network Longitude : " + locationNetwork!!.longitude)
                        }
                    }
                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

                    }
                    override fun onProviderEnabled(provider: String) {

                    }
                    override fun onProviderDisabled(provider: String) {

                    }
                })
                val localNetworkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if (localNetworkLocation != null)
                    locationNetwork = localNetworkLocation
            }
            if (locationGps != null && locationNetwork != null) {

                if (locationGps!!.accuracy > locationNetwork!!.accuracy) {

                    tv_result.append("\nNetwork ")
                    tv_result.append("\nLatitude : " + locationNetwork!!.latitude)
                    tv_result.append("\nLongitude : " + locationNetwork!!.longitude)
                   Log.d("CodeAndroidLocation", " Network Latitude : " + locationNetwork!!.latitude)
                   Log.d("CodeAndroidLocation", " Network Longitude : " + locationNetwork!!.longitude)
                }else{

                    tv_result.append("\nGPS ")
                    tv_result.append("\nLatitude : " + locationGps!!.latitude)
                    tv_result.append("\nLongitude : " + locationGps!!.longitude)
                    Log.d("CodeAndroidLocation", " GPS Latitude : " + locationGps!!.latitude)
                    Log.d("CodeAndroidLocation", " GPS Longitude : " + locationGps!!.longitude)
                }
            }
        }else {

            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }
    private fun checkPermission(permissionArray: Array<String>): Boolean {

        var allSuccess = true
        for (i in permissionArray.indices) {

            if (checkCallingOrSelfPermission(permissionArray[i]) == PackageManager.PERMISSION_DENIED)
                allSuccess = false
        }
        return allSuccess
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST) {

            var allSuccess = true
            for (i in permissions.indices) {

                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {

                    allSuccess = false
                    val requestAgain = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(permissions[i])
                    if (requestAgain) {

                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                    }else {

                        Toast.makeText(this, "Go to settings and enable the permission", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            if (allSuccess)
                enableView()
        }
    }
}


*/