package com.example.dailynewsup

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(val context: Context,val article: List<Articles>):RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false)
        return ArticleViewHolder(view)
    }
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article=article[position]
        holder.newsHeading.text=article.title
        holder.newsDisp.text=article.description
        Glide.with(context).load(article.urlToImage).into(holder.newsImage)
        holder.itemView.setOnClickListener {
            val intent=Intent(context,DetailActivity::class.java)
            intent.putExtra("URL",article.urlToImage)
            context.startActivity(intent)
            Toast.makeText(context,article.title,Toast.LENGTH_LONG).show()
        }
    }

    override fun getItemCount(): Int {
      return article.size
    }

    class ArticleViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    {
        var newsImage=itemView.findViewById<ImageView>(R.id.image_view);
        var newsHeading=itemView.findViewById<TextView>(R.id.heading);
        var newsDisp=itemView.findViewById<TextView>(R.id.description);
    }
}