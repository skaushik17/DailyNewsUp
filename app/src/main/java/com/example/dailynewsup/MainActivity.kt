package com.example.dailynewsup

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var newsAdapter:NewsAdapter
    lateinit var recyclerViewList:RecyclerView
    private var mInterstitialAd: InterstitialAd? = null
    var article: MutableList<Articles> = mutableListOf<Articles>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getNews()
        recyclerViewList=findViewById<RecyclerView>(R.id.newList)
        MobileAds.initialize(this)

        // admob code
        var adRequest = AdRequest.Builder().build()
        InterstitialAd.load(applicationContext,"ca-app-pub-3940256099942544~3347511713", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError?.message)
                mInterstitialAd = null
            }
            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded")
                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(this@MainActivity)
                }
                mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback()
                {
                    override fun onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent()
                        adRequest = AdRequest.Builder().build()
                    }
                }
            }
        })

    }

    private fun getNews() {
        val news=NewsService.newsInstance.getHeadLines("in",1)
        news.enqueue(object :Callback<News>{
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news=response.body()
                if(news !=null) {
                    newsAdapter= NewsAdapter(this@MainActivity,news.articles)
                    recyclerViewList.adapter=newsAdapter
                    recyclerViewList.layoutManager= LinearLayoutManager(this@MainActivity)
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Toast.makeText(applicationContext,"Error fetching",Toast.LENGTH_SHORT).show()
            }
        })
    }
}
