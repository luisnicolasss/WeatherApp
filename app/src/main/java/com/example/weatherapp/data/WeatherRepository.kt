package com.example.weatherapp.data

import com.example.weatherapp.data.model.WeatherModel
import com.example.weatherapp.data.network.WeatherService
import retrofit2.Response

class WeatherRepository(private val weatherService: WeatherService) {

    suspend fun getWeather(cityName: String): Response<WeatherModel> {
        return weatherService.getWeather(cityName)
    }
}