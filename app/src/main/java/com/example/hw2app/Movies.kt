package com.example.hw2app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Movies : AppCompatActivity(), MovieAdapter.OnItemClickListener {

    private lateinit var movieList : Array<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        movieList = MovieData().movieList

        val recyclerMovies = findViewById<RecyclerView>(R.id.recycler_movies)

        recyclerMovies.adapter = MovieAdapter(movieList, this)
        recyclerMovies.layoutManager = LinearLayoutManager(this)
        recyclerMovies.setHasFixedSize(true)

    }

    override fun onItemClick(position: Int) {

        val chosen = movieList[position]
        val intent = Intent(this, MovieDetail::class.java)

        intent.putExtra("movieInfo", chosen)
        startActivity(intent)
        onPause()
    }
}