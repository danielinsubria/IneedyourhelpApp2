package com.example.ineedyourhelpapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {

    lateinit var usersDBHelper: UsersDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        usersDBHelper = UsersDBHelper(this)
    }

    fun addUser(v: View) {
        var name = this.edittext_name.text.toString()
        var number = this.edittext_number.text.toString()
        if ((name != null && name.length > 2) && (number != null && number.length > 2)) {
            Toast.makeText(this@MainActivity2, "Utente aggiunto", Toast.LENGTH_SHORT).show()
            var result = usersDBHelper.insertUser(UserModel(name = name, number = number))
        } else {
            Toast.makeText(this@MainActivity2, "nome o numero non valido", Toast.LENGTH_SHORT).show()
        }

        //clear all edittext s
        this.edittext_number.setText("")
        this.edittext_name.setText("")
        //this.textview_result.text = "Added user "
        this.ll_entries.removeAllViews()

        showAllUsers(v)

    }

    fun deleteUser(v: View) {
        var name = this.edittext_name.text.toString()
        if (name != null && name.length > 2) {
            val result = usersDBHelper.deleteUser(name)
            this.textview_result.text = "Deleted user"
        } else {
            this.textview_result.text = "Inserire il nome "
        }
        this.ll_entries.removeAllViews()
    }

    fun showAllUsers(v: View) {
        var users = usersDBHelper.readAllUsers()
        this.ll_entries.removeAllViews()
        users.forEach {
            var tv_user = TextView(this)
            tv_user.textSize = 30F
            tv_user.text = it.name.toString() + " - " + it.number.toString()
            this.ll_entries.addView(tv_user)
        }
        this.textview_result.text = "Find " + users.size + " users"
    }
}

