package com.example.ineedyourhelpapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main3.*
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

/*
class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
    }
    fun salvaMessaggio(v: View){
        val message = textViewMessage.text.toString()
    }
}
*/

class MainActivity3 : AppCompatActivity() {
    var textBox: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        textBox = findViewById<View>(R.id.textViewMessage) as EditText
    }




















  /*  @SuppressLint("WorldReadableFiles")
    fun Salva(view: View?) {
        val str = textBox!!.text.toString()
        try {
            val fOut = openFileOutput("mio_file.txt", MODE_WORLD_READABLE)
            val osw = OutputStreamWriter(fOut)
            osw.write(str)
            osw.flush()
            osw.close()
            Toast.makeText(
                baseContext,
                "Dati salvati correttamente.",
                Toast.LENGTH_SHORT
            ).show()
            textBox!!.setText("")
        } catch (ioe: IOException) {
            ioe.printStackTrace()
        }
    }

   fun Carica(view: View?) {
        try {
            val fIn = openFileInput("mio_file.txt")
            val isr = InputStreamReader(fIn)
            var inputBuffer = CharArray(size = 150)
            var s: String? = ""
            var charRead: Int
            while (isr.read(inputBuffer).also { charRead = it } > 0) {
                val readString = String(inputBuffer, 0, charRead)
                s += readString
                inputBuffer = CharArray(size = 150)
            }
            textBox!!.setText(s)
            Toast.makeText(
                baseContext,
                "Dati caricati correttamente.",
                Toast.LENGTH_SHORT
            ).show()
        } catch (ioe: IOException) {
            ioe.printStackTrace()
        }
    } */

}

