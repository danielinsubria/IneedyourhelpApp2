package com.example.ineedyourhelpapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.telephony.SmsManager
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.postDelayed
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*
import java.util.concurrent.Executors
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), LocationListener {
    var invio= true //variabile che ci serve per gli invii dell messaggio
    lateinit var usersDBHelper: UsersDBHelper //variabile per l'uso di SQLite

    //variabili per l'utilizzo del GPS
    private lateinit var locationManager: LocationManager
    private lateinit var tvGpsLocation: TextView
    private val locationPermissionCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usersDBHelper = UsersDBHelper(this)
        //prima di avviare l'emergenza bisogna aspettare che compaia la posizione questo perche l'operazione richiede qualche secondo all'avvio
        Toast.makeText(this@MainActivity, "PRIMA DI AVVIARE L'EMERGENZA ASPETTARE LAPOSIZIONE ", Toast.LENGTH_SHORT).show()
        //richiama getlocation per ottenere laposizione quando si avvia l'app, la posizione poi si aggiorna automatomaticamente
        getLocation()
    }

    //apertura activity dei contatti
    fun OpenSecondActivity(v: View) {
        val intent = Intent(this@MainActivity, MainActivity2::class.java)
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
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
    //invio messaggio ai contatti nel DataBase con la posizione aggiornata
    fun inviaMessaggio(view: View) {
        val msg= findViewById(R.id.emergenza) as TextView
        msg.setText("EMERGENZA ATTIVA") //cambia la label sotto l'emergenza in modo che si capisca che l'emergenza é attiva

        val buttonstop = findViewById(R.id.button) as Button
        buttonstop.visibility = View.VISIBLE //visibilità tasto stop

        //invio del messaggio ai contatti con la posizione
        thread(start = true) {
            invio=true //serve per ciclare infinitamente all'interno del thread in modo che il programma non si blocchi e che invii
            while (invio) {//messaggi di emergenza continui ogni 10 s anche quando l'applicazione é in background
                val manager = SmsManager.getDefault()
                //imposta la struttura del messaggio testo + posizione aggiornata
                val message: String = "!!!MESSAGGIO DI EMERGENZA!!! HO BISOGNO DI AIUTO: la mia posizione è " + tvGpsLocation.text
                var users = usersDBHelper.readAllUsers()

                users.forEach {//recupera tutti i contatti e invia i messaggi
                    var number = it.number.toString() //conversione del numero a stringa per inviarlo
                    manager.sendTextMessage(number, null, message, null, null) //invio messaggio automatico
                }
                Thread.sleep(10000) //tempo di invio del messaggio

            }
        }
    }
    //funzione che ferma l 'invio dei messaggi dopo aver cliccato il tasto "Stop"
    fun stopinvio(v: View) {
        val msg= findViewById(R.id.emergenza) as TextView
        msg.setText("EMERGENZA DISATTIVA") //cambia la textvew in modo che si capisca che l'emergenza é disattiva
            invio=false; // blocca il ciclo infiinito nel thread in modo che non vengano piu inviati i messaggi

            }
        }






