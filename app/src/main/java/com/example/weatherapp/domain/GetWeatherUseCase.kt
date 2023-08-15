package com.example.weatherapp.domain

import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.data.model.WeatherModel
import retrofit2.Response

class GetWeatherUseCase(private val weatherRepository: WeatherRepository) {

    suspend operator fun invoke(cityName: String): Response<WeatherModel> = weatherRepository.getWeather(cityName)
}