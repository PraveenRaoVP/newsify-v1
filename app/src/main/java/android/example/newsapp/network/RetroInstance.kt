package android.example.newsapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetroInstance {
    companion object {
        const val NEWS_API_BASE_URL = "https://inshortsapi.vercel.app/"
        const val WEATHER_API_BASE_URL = "https://api.tomorrow.io/v4/"
        const val WEATHER_API_KEY = "6OLtL9jVdKdPe0NxHP1Txfg5gwJuJ77H"

        fun getRetroInstance(url: String): Retrofit {
            return Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}