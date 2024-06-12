package android.example.newsapp.network.weather

import android.example.newsapp.models.WeatherData
import android.example.newsapp.network.RetroInstance
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofit = RetroInstance.getRetroInstance(RetroInstance.WEATHER_API_BASE_URL)

interface WeatherAPIInterface {
    @GET("weather/realtime")
    suspend fun getWeather(@Query("location") location: String, @Query("apikey") apiKey: String) : WeatherData
}

object WeatherAPIService {
    val retrofitService: WeatherAPIInterface by lazy {
        retrofit.create(WeatherAPIInterface::class.java)
    }
}