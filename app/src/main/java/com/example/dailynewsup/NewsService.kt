package com.example.dailynewsup

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


//https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=API_KEY
const val BASE_URL="https://newsapi.org/"
const val API_KEY="bb64fbda063a486a841327908006f83f"
interface NewsInterface {
    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getHeadLines(@Query("country")country:String,@Query("page")page:Int):Call<News>
}
object NewsService{
  val newsInstance:NewsInterface
  init {
      val retrofit=Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .build()
      newsInstance=retrofit.create(NewsInterface::class.java)
  }
}