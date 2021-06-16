  package com.example.hw2app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.hw2app.databinding.ActivityMainBinding
import com.google.android.gms.maps.OnMapReadyCallback

  class MainActivity : AppCompatActivity(){

     private lateinit var binding: ActivityMainBinding

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)

         binding = ActivityMainBinding.inflate(layoutInflater)
         setContentView(R.layout.activity_main)

         val movieBtn = findViewById<Button>(R.id.movieBtn)
         val sendBtn = findViewById<Button>(R.id.sendBtn)
         val mapBtn = findViewById<Button>(R.id.mapBtn)
         val trafficBtn = findViewById<Button>(R.id.trafficBtn)

         trafficBtn.setOnClickListener {
//             Toast.makeText(this, "Hi there! This is a ${this.getString(R.string.nwBtn)} Button!", Toast.LENGTH_LONG).show()
             val intent = Intent(this, TrafficCameras::class.java)
             startActivity(intent)
         }

         movieBtn.setOnClickListener {
//             Toast.makeText(this, "Hi there! This is the ${this.getString(R.string.movieBtn)} Button!", Toast.LENGTH_LONG).show()
             val intent = Intent(this, Movies::class.java)
             startActivity(intent)
         }

         sendBtn.setOnClickListener {
             Toast.makeText(this, "Hi there! This is a ${this.getString(R.string.sendBtn)} Button.!", Toast.LENGTH_LONG).show()
         }

         mapBtn.setOnClickListener {
//             Toast.makeText(this, "this is the ${this.getString(R.string.mapBtn)}", Toast.LENGTH_LONG).show()
             val intent = Intent(this, Map::class.java)
             startActivity(intent)
         }

     }
 }
