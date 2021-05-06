package com.example.ineedyourhelpapp

import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.database.*
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        var name1: EditText = findViewById(R.id.editTextName1)
        var number1: EditText = findViewById(R.id.editTextNumber1)
        var name2: EditText = findViewById(R.id.editTextName2)
        var number2: EditText = findViewById(R.id.editTextNumber2)
        var name3: EditText = findViewById(R.id.editTextName3)
        var number3: EditText = findViewById(R.id.editTextNumber3)

        var btnInsert: Button = findViewById(R.id.buttonInsert)
        var btnDelete: Button = findViewById(R.id.buttonDelete)
        var btnSave: Button = findViewById(R.id.buttonSave)

        val openHelper = DataBaseHelper(this)
        val db = openHelper

        btnInsert.setOnClickListener(View.OnClickListener {
            var sname1: String = name1.text.toString()
            var snumber1: String = number1.text.toString()
            var sname2: String = name2.text.toString()
            var snumber2: String = number2.text.toString()
            var sname3: String = name3.text.toString()
            var snumber3: String = number3.text.toString()
            db.writableDatabase
            onInsert(sname1, snumber1, sname2, snumber2, sname3, snumber3)
        })

         fun onInsert(sname1: String, snumber1:String, sname2: String, snumber2:String, sname3: String, snumber3:String){
            val contentValues = ContentValues()
            contentValues.put(COL_NAME, sname1)
            contentValues.put(COL_NUM, snumber1)
            contentValues.put(COL_NAME, sname2)
            contentValues.put(COL_NUM, snumber2)
            contentValues.put(COL_NAME, sname3)
            contentValues.put(COL_NUM, snumber3)
        }

        fun onSave(v: View) {

        }

        fun onDelete(v: View) {

        }
    }

    private fun onInsert(sname1: String, snumber1: String, sname2: String, snumber2: String, sname3: String, snumber3: String) {
        val contentValues = ContentValues()
        contentValues.put(COL_NAME, sname1)
        contentValues.put(COL_NUM, snumber1)
        contentValues.put(COL_NAME, sname2)
        contentValues.put(COL_NUM, snumber2)
        contentValues.put(COL_NAME, sname3)
        contentValues.put(COL_NUM, snumber3)
    }


}