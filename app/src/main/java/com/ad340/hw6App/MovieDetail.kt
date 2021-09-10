package com.ad340.hw6App

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.ad340.hw6App.R

class MovieDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val info = intent.extras?.getStringArray("movieInfo")

        findViewById<TextView>(R.id.detail_title).text = info?.get(0)
        findViewById<TextView>(R.id.detail_year).text = info?.get(1)
        findViewById<TextView>(R.id.detail_description).text = info?.get(4)

        supportActionBar?.title = info?.get(0)
    }
}