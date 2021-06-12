package com.example.ineedyourhelpapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.telephony.SmsManager
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), LocationListener {

    lateinit var usersDBHelper: UsersDBHelper

    private lateinit var locationManager: LocationManager
    private lateinit var tvGpsLocation: TextView
    private val locationPermissionCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usersDBHelper = UsersDBHelper(this)


        getLocation()

        val button: Button = findViewById(R.id.getLocation)
        button.setOnClickListener {
            getLocation()
        }
    }


    //apertura activity dei contatti
    fun OpenSecondActivity(v: View) {
        val intent= Intent(this@MainActivity, MainActivity2::class.java)
        startActivity(intent)
    }

    //ottenere posizione
     fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }
    //sovrascrivere la posizione cambiata
    override fun onLocationChanged(location: Location) {
        tvGpsLocation = findViewById(R.id.textViewPosizione)
        tvGpsLocation.text = "Latitudine: " + location.latitude + " , Longitudine: " + location.longitude
    }
    //richiesta permesso di locazione GPS
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

    fun inviaMessaggio(view: View) {//invio messaggio ai contatti nel db con la posizione
       /* var tstart=true
        thread(start = true) {
            while(tstart) {
                Thread.sleep(500)
                getLocation()
            }
             this.runOnUiThread {
                 imageView.setImageBitmap(bmp)
             }
        }*/
        val manager = SmsManager.getDefault()
        //val number:String = "3473892"
        //var result = usersDBHelper.giveData(UserModel( number = number))
        val message:String = "la mia posizione è "+ tvGpsLocation.text
        var users = usersDBHelper.readAllUsers()
        //this.ll_entries.removeAllViews()
        users.forEach {
            // var tv_user = TextView(this)
            var number= it.number.toString()
            manager.sendTextMessage(number, null, message, null, null)
            //tv_user.textSize = 30F
            //tv_user.text = it.name.toString() + " - " + it.number.toString()
            // this.ll_entries.addView(tv_user)
        }
        //manager.sendTextMessage(number, null, message, null, null)
        }

    fun stopinvio(v: View){

    }
    fun obtainNumber(v: View) {
        var users = usersDBHelper.readAllUsers()
        //this.ll_entries.removeAllViews()
        users.forEach {
           // var tv_user = TextView(this)
            var number= it.number.toString()
           // manager.sendTextMessage(number, null, message, null, null)
            //tv_user.textSize = 30F
            //tv_user.text = it.name.toString() + " - " + it.number.toString()
           // this.ll_entries.addView(tv_user)
        }
       // this.textview_result.text = "Find " + users.size + " users"
    }
}



