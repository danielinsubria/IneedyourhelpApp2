package com.example.ineedyourhelpapp

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun OpenSecondActivity(v:View) {
        val intent= Intent(this@MainActivity, MainActivity2::class.java)
        startActivity(intent)
    }
    fun OpenThirdActivity(v:View) {
        val intent= Intent(this@MainActivity, MainActivity3::class.java)
        startActivity(intent)
    }
    fun inviaMessaggio(v:View){

        val uri: Uri = Uri.parse("smsto:000000012223")
    }
}

