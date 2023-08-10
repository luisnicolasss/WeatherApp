package com.example.weatherapp.data.network

import com.example.weatherapp.data.model.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiClient{
      @GET("data/2.5/weather?&units=metric&APPID=cc90b9364d07d48f0b16563f0d370dad")
      suspend fun getWeather(@Query("q") cityName: String): Response<WeatherModel>
}