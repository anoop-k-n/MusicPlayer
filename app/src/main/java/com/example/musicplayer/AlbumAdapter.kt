package com.example.musicplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class AlbumAdapter(private val albumList : ArrayList<album>) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    class AlbumViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val albumImage : ShapeableImageView = itemView.findViewById(R.id.albumImage)
        val albumTitle : TextView = itemView.findViewById(R.id.albumName)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumAdapter.AlbumViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.album_view,parent,false)
        return AlbumViewHolder(itemView)
            }

    override fun onBindViewHolder(holder: AlbumAdapter.AlbumViewHolder, position: Int) {
        //holder.title.text = albumList[position]
        val currentAlbum = albumList[position]
        holder.albumImage.setImageResource(currentAlbum.albumImage)

    }

    override fun getItemCount(): Int {
        return albumList.size

    }
}