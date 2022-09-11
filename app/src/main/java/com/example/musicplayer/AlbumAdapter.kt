package com.example.musicplayer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.imageview.ShapeableImageView


class AlbumAdapter(private val context: Context, private val musicList : ArrayList<Music>) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    class AlbumViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val image : ShapeableImageView = itemView.findViewById(R.id.albumImage)
        val album : TextView = itemView.findViewById(R.id.album)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumAdapter.AlbumViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.album_view,parent,false)

        return AlbumViewHolder(itemView)
            }

    override fun onBindViewHolder(holder: AlbumAdapter.AlbumViewHolder, position: Int) {
        holder.album.text = musicList[position].album
        Glide.with(context)
            .load(musicList[position].artUri)
                .apply(RequestOptions().placeholder(R.drawable.music_player_icon_splash_screen).centerCrop())
                .into(holder.image)
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

}

