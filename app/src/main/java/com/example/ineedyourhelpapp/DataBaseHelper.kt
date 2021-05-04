package com.example.ineedyourhelpapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
val DATABASE_VERSION = 1
val DATABASE_NAME = "ListViewApp.db"
val TABLENAME = "Users"
val COL_NAME = "name"
val COL_MESS = "messaggio"
val COL_NUM = "id"
class DataBaseHelper(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION  ) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLENAME + " (" +
                COL_NUM + " INT," +
                COL_NAME + " VARCHAR(256)," +
                COL_MESS + " VARCHAR(256))"
        db?.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLENAME)
        onCreate(db)
    }

    fun insertData(user: User) { //INSERIAMO ALL'INTERNO UN UTENTE
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_NAME, user.name)
        contentValues.put(COL_MESS, user.surname)
        contentValues.put(COL_NUM, user.num)

        val result = database.insert(TABLENAME, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun readData(): MutableList<User> {

        val list: MutableList<User> = ArrayList();
        val db = this.readableDatabase
        val query = "Select * from $TABLENAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val user = User()
                user.num = result.getInt(result.getColumnIndex(COL_NUM))
                user.name = result.getString(result.getColumnIndex(COL_NAME))
                user.surname = result.getString(result.getColumnIndex(COL_MESS))
                list.add(user)
            }while (result.moveToNext())
        }
        return list
    }
}
class User(var name: String, var surname: String) {
    var num: Int=0
    constructor(): this("" ,"") //costruttore per creare una istanza oggetto vuota
}



