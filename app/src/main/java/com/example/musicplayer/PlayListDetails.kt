package com.example.musicplayer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.musicplayer.databinding.ActivityPlayListDetailsBinding

class PlayListDetails : AppCompatActivity() {
    lateinit var binding: ActivityPlayListDetailsBinding
    lateinit var adapter: AlbumAdapter
    companion object{
        var currentPlayListPos: Int = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayListDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currentPlayListPos = intent.extras?.get("index") as Int

        binding.PlaylistDetailsRecyclerView.setItemViewCacheSize(10)
        binding.PlaylistDetailsRecyclerView.setHasFixedSize(true)
        binding.PlaylistDetailsRecyclerView.layoutManager = LinearLayoutManager(this)

        // temporarily initialize object to avoid errors
        PlaylistsFragment.musicPlaylist.ref[currentPlayListPos].playlist.addAll(LibraryFragment.MusicListLibrary)

        adapter = AlbumAdapter(this,PlaylistsFragment.musicPlaylist.ref[currentPlayListPos].playlist, playlistDetails = true)
        binding.PlaylistDetailsRecyclerView.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        binding.playlistNameinDetails.text = PlaylistsFragment.musicPlaylist.ref[currentPlayListPos].name
        // set image of first song as playlist image
        if(adapter.itemCount>0){
            Glide.with(this)
                .load(PlaylistsFragment.musicPlaylist.ref[currentPlayListPos].playlist[0].artUri)
                .apply(RequestOptions().placeholder(R.drawable.music_player_icon_splash_screen).centerCrop())
                .into(binding.PlaylistImgInDetails)
            // make the shuffle visible now
            binding.shuffleBtninPlaylistDetails.visibility = View.VISIBLE

        }

    }
}