package com.example.hw2app

import com.example.hw2app.models.Camera
import retrofit2.Call
import retrofit2.http.GET

interface ApiCall {
    @GET("Data?zoomId=13&type=2")
    fun getCameraData(): Call<Camera>
}