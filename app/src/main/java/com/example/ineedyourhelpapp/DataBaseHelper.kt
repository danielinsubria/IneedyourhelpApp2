package com.example.ineedyourhelpapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

//Cols
val DATABASE_VERSION = 1
val DATABASE_NAME = "Contacts.db"
val TABLENAME = "Users"
val COL_NAME = "name"
val COL_NUM = "number"

class DataBaseHelper(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION  ) {
    //crea tabella
    val createTable = "CREATE TABLE " + TABLENAME + " (" +
            COL_NUM + " VARCHAR(10)," +
            COL_NAME + " VARCHAR(256)," +
            " )"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //cancella tabella se esiste
        db?.execSQL("DROP TABLE IF EXISTS " + TABLENAME)
        //crea di nuovo la tabella
        onCreate(db)
    }
/*
    //funzione che crea un utente
    fun insertData(user: User) {
        val database = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(COL_NAME, user.name)
        contentValues.put(COL_NUM, user.num)

        //inserire righe
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

        //interrogazione della tabella User
        val query = "Select * from $TABLENAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val user = User()
                user.num = result.getString(result.getColumnIndex(COL_NUM))
                user.name = result.getString(result.getColumnIndex(COL_NAME))
                list.add(user)
            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }
*/}
