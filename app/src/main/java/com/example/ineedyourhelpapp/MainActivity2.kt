package com.example.ineedyourhelpapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ListAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

            val db = DataBaseHelper(this)


        val users = arrayOf(
               "Pippo Baudo",
                "Virat Kohli", "Rohit Sharma", "Steve Smith",
                "Kane Williamson", "Ross Taylor", "Mario Rossi",
                "Giuseppe verdi", "Pippo Baudo"
        )
        //users = db.readData()
// pass data to the Adapter
        //list_view.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, users)

            list_view.adapter = MyAdapter(this, users)

        }

    class MyAdapter(private val context: Context, val data: MutableList<User>) : BaseAdapter(), ListAdapter {
        override fun getCount(): Int {
            return data.size
        }
        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            var newView = convertView
            if (convertView == null)
                newView = LayoutInflater.from(context).inflate(R.layout.row, parent,false)
            if (newView != null){
                val personName: TextView = newView.findViewById(R.id.textViewName)
                val personSurname:TextView = newView.findViewById(R.id.textViewSurname)
                val personPos:TextView = newView.findViewById(R.id.textViewNumber)
               // val parts = data[position].split(" ")
                personName.text = data[position].name
                personSurname.text = data[position].surname
                personPos.text = "${data[position].num}​​​​​"
            }
            return newView
        }
    }



}