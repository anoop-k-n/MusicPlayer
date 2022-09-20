package com.example.musicplayer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.databinding.PlaylistViewBinding

class PlaylistAdapter(private val context: Context, private var playlistList : ArrayList<Playlist>) : RecyclerView.Adapter<PlaylistAdapter.MyHolder>() {

    class MyHolder(binding: PlaylistViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.ImgInPlaylists
        val name = binding.PlaylistName
        val root = binding.root


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyHolder {
        return MyHolder(PlaylistViewBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.name.text = playlistList[position].name
    }

    override fun getItemCount(): Int {
        return playlistList.size
    }

    fun refreshPlaylist(){
        playlistList = ArrayList()
        playlistList.addAll(PlaylistsFragment.musicPlaylist.ref)
        notifyDataSetChanged()
    }


}
