package com.example.weatherapp.data.network


import com.example.weatherapp.data.model.WeatherModel
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class WeatherService {


    private val BASE_URL = "https://api.openweathermap.org/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiClient: WeatherApiClient = retrofit.create(WeatherApiClient::class.java)

    suspend fun getWeather(cityName: String): Response<WeatherModel> {
        return apiClient.getWeather(cityName)
    }


}