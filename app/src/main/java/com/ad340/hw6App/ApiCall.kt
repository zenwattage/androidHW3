package com.ad340.hw6App

import com.ad340.hw6App.models.Camera
import retrofit2.Call
import retrofit2.http.GET

interface ApiCall {
    @GET("Data?zoomId=13&type=2")
    fun getCameraData(): Call<Camera>
}