package android.example.newsapp.network.news

import android.example.newsapp.models.NewsData
import android.example.newsapp.network.RetroInstance
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofit = RetroInstance.getRetroInstance(RetroInstance.NEWS_API_BASE_URL)

interface NewsAPIInterface {
    @GET("news")
    suspend fun getNewsByCategory(@Query("category") category: String) : NewsData
}

object NewsAPIService {
    val retrofitService: NewsAPIInterface by lazy {
        retrofit.create(NewsAPIInterface::class.java)
    }
}