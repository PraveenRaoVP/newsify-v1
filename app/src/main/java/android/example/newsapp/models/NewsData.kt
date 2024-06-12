package android.example.newsapp.models

data class NewsData(
    val category: String,
    val `data`: List<News>,
    val success: Boolean
)