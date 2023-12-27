package com.example.koplakmungkin.ui.main.koprasell

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.koplakmungkin.R
import com.example.koplakmungkin.data.model.FeedData

class JualKopraAdapter(private var feedList: List<FeedData>) : RecyclerView.Adapter<JualKopraAdapter.FeedViewHolder>(){
    class FeedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val usernameTv: TextView = itemView.findViewById(R.id.usernameTv)
        val imageIv: ImageView = itemView.findViewById(R.id.ivnews)
        val priceTv: TextView = itemView.findViewById(R.id.titleHargaTv)
        val contactTv: TextView = itemView.findViewById(R.id.contactTv)
        val gradeTv: TextView = itemView.findViewById(R.id.gradeTv)
        val descriptionTv: TextView = itemView.findViewById(R.id.tittleTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.feed_layout, parent, false)
        return FeedViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return feedList.size
    }

    @SuppressLint("StringFormatMatches")
    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val feed = feedList[position]

        val hargaText = holder.itemView.context.getString(R.string.harga_text, feed.price)
        val contactText = holder.itemView.context.getString(R.string.contact, feed.contact)

        holder.usernameTv.text = feed.username
        holder.descriptionTv.text = feed.description
        holder.priceTv.text = hargaText
        holder.contactTv.text = contactText
        holder.gradeTv.text = feed.grade
        Glide.with(holder.imageIv.context)
            .load(feed.image)
            .into(holder.imageIv)
    }
    fun updateData(newFeedList: List<FeedData>) {
        feedList = newFeedList
        notifyDataSetChanged()
    }
}