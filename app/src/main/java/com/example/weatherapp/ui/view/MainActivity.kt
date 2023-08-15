package com.example.weatherapp.ui.view

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.putString
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.edit
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.ui.viewmodel.WeatherViewModel

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

   private lateinit var binding : ActivityMainBinding

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor


    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

         GET = getSharedPreferences(packageName, MODE_PRIVATE)
         SET = GET.edit()

        val cityName = GET.getString("cityName", "argentina")?.toLowerCase()
        binding.edtCityName.setText(cityName)

        weatherViewModel.refreshWeather(cityName!!)

        getLiveData()

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.llDataView.visibility = View.GONE
            binding.tvError.visibility = View.GONE
            binding.pbLoading.visibility = View.GONE

            val newCityName = GET.getString("cityName", cityName)?.toLowerCase()
            binding.edtCityName.setText(newCityName)
            weatherViewModel.refreshWeather(newCityName!!)
            binding.swipeRefreshLayout.isRefreshing = false

            binding.imgSearchCity.setOnClickListener {
                val newCityName = binding.edtCityName.text.toString()
                SET.putString("cityName", newCityName)
                SET.apply()
                weatherViewModel.refreshWeather(newCityName)
                getLiveData()
                Log.i(TAG, "onCreate: $newCityName")
            }


        }

    }

    private fun getLiveData() {

        weatherViewModel.weatherModel.observe(this, Observer { weatherModel ->
            weatherModel?.let {
                binding.llDataView.visibility = View.VISIBLE

                binding.tvCountryCode.text = it.sys.country.toString()
                binding.cityName.text = it.name.toString()

                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/${it.weather[0].icon}@2x.png")
                    .into(binding.imgWeatherIcon)

                binding.tvDegree.text = "${it.main.temp}°C"
                binding.tvHumidity.text = "${it.main.humidity}%"
                binding.tvSpeed.text = it.wind.speed.toString()
                binding.tvLat.text = it.coord.lat.toString()
                binding.tvLon.text = it.coord.lon.toString()
            }
        })

        weatherViewModel.isLoading.observe(this, Observer { isLoading ->
            isLoading?.let {
                binding.pbLoading.isVisible = isLoading
                if (isLoading) {
                    binding.tvError.visibility = View.GONE
                    binding.llDataView.visibility = View.GONE
                }
            }
        })

        weatherViewModel.isError.observe(this, Observer { isError ->
            isError?.let {
                binding.tvError.isVisible = isError
                if (isError) {
                    binding.pbLoading.visibility = View.GONE
                    binding.llDataView.visibility = View.GONE
                }
            }
        })
    }
}