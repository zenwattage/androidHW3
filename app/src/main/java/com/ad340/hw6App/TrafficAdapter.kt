package com.ad340.hw6App

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ad340.hw6App.R
import com.ad340.hw6App.models.Camera
import com.squareup.picasso.Picasso


const val API_URL = "https://www.seattle.gov/trafficcams/images/"

class TrafficAdapter(private val apiResponse: Camera) :
    RecyclerView.Adapter<TrafficAdapter.TrafficViewHolder>(){

    inner class TrafficViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.traffic_cam_title)
        val img: ImageView = itemView.findViewById(R.id.traffic_cam_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrafficViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.traffic_cam, parent, false)
        return TrafficViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrafficViewHolder, position: Int) {
        val fullImageUrl = API_URL + apiResponse.Features[position].Cameras[0].ImageUrl

        holder.title.text = apiResponse.Features[position].Cameras[0].Description
        Picasso.get().load(fullImageUrl).into(holder.img)
        holder.img.contentDescription = apiResponse.Features[position].Cameras[0].Description
    }

    override fun getItemCount(): Int {
        return apiResponse.Features.size
    }
}