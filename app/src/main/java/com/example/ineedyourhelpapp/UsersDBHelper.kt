package com.example.ineedyourhelpapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

import java.util.ArrayList

class UsersDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {//definizione dell'onupgrade
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertUser(user: UserModel): Boolean {
        // trasforma il repositori in modalita scrittura
        val db = writableDatabase

        // crea una mappa di valori con chiave il nome
        val values = ContentValues()
       // values.put(DBContract.UserEntry.COLUMN_USER_ID, user.userid)
        values.put(DBContract.UserEntry.COLUMN_NAME, user.name)
        values.put(DBContract.UserEntry.COLUMN_NUMBER, user.number)

        //inserisce una nuova riga e ritorna la primary key della riga creata
        val newRowId = db.insert(DBContract.UserEntry.TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteUser(username: String): Boolean {
        // mette il db in modalita scrittura
        val db = writableDatabase
        // Definisce il where
        val selection = DBContract.UserEntry.COLUMN_NAME + " LIKE ?"
        // specifica gli argomenti
        val selectionArgs = arrayOf(username)
        //SQL statement.
        db.delete(DBContract.UserEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readUser(username: String): ArrayList<UserModel> {
        val users = ArrayList<UserModel>() // utilizziamo il modello creato per contenere i dati
        val db = writableDatabase //db in scrittura
        var cursor: Cursor? = null
        try { //legge tutti gli utenti e i relativi numeri
            cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME + " WHERE " + DBContract.UserEntry.COLUMN_NAME+ "='" + username + "'", null)
        } catch (e: SQLiteException) {
            // se la table non e presente la crea
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var name: String
        var number: String
        if (cursor!!.moveToFirst()) { //mette i dati del cursor nella struttura user model
            while (cursor.isAfterLast == false) { // finche il cursor contiene qualcosa continua
                name = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NAME)) //reperisce nome
                number = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NUMBER)) //reperisce numero

                users.add(UserModel( name, number)) // aggiunge l'utente all'usermodel
                cursor.moveToNext() // manda avanti la posizione del cursore per leggere il nuovo dato
            }
        }
        return users
    }
    //legge al massimo 5 utenti dopo il 5 non ne inserisce piu
    fun readAllUsers(): ArrayList<UserModel> {
        val users = ArrayList<UserModel>()
        val db = writableDatabase //db in writable
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME +" LIMIT 5", null)//lettura dei 5 utenti
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        //var userid: String
        var name: String
        var number: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
               // userid = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_USER_ID))
                name = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NAME))
                number = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NUMBER))

                users.add(UserModel(name, number))
                cursor.moveToNext()
            }
        }
        return users
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "FeedReader.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.UserEntry.TABLE_NAME + " (" +
                    DBContract.UserEntry.COLUMN_NAME + " TEXT," +
                    DBContract.UserEntry.COLUMN_NUMBER + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.UserEntry.TABLE_NAME
    }

}