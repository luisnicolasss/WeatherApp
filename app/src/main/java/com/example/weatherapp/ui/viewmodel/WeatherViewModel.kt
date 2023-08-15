package com.example.weatherapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.data.model.WeatherModel
import com.example.weatherapp.data.network.WeatherService
import com.example.weatherapp.domain.GetWeatherUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class WeatherViewModel: ViewModel() {

    val weatherModel = MutableLiveData<WeatherModel>()
    val isLoading = MutableLiveData<Boolean>()
    val isError = MutableLiveData<Boolean>()

    private val weatherRepository = WeatherRepository(WeatherService())
    private val getWeatherUseCase = GetWeatherUseCase(weatherRepository)

    fun refreshWeather(cityName: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val response = getWeatherUseCase(cityName)

            if (response.isSuccessful) {
                weatherModel.postValue(response.body())
                isLoading.postValue(false)
                isError.postValue(false)
            } else {
                isLoading.postValue(false)
                isError.postValue(true)
            }
        }
    }
}