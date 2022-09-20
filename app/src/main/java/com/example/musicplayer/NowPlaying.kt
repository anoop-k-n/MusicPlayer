package com.example.musicplayer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.musicplayer.databinding.FragmentNowPlayingBinding


class NowPlaying : Fragment() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var binding: FragmentNowPlayingBinding
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_now_playing, container, false)
        binding = FragmentNowPlayingBinding.bind(view)
        binding.root.visibility = View.INVISIBLE
        binding.playPauseBtnNowPlaying.setOnClickListener{
            if(PlayerActivity.isPlaying) pauseMusic()
            else playMusic()
        }
        binding.root.setOnClickListener{
            val intent = Intent(requireContext(), PlayerActivity::class.java)
            intent.putExtra("index",PlayerActivity.songPosition)
            intent.putExtra("class","NowPlaying")
            ContextCompat.startActivity(requireContext(),intent,null)
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        if(PlayerActivity.musicService != null){
            binding.root.visibility = View.VISIBLE
            Glide.with(this)
                .load(PlayerActivity.musicListPA[PlayerActivity.songPosition].artUri)
                .apply(RequestOptions().placeholder(R.drawable.music_player_icon_splash_screen).centerCrop())
                .into(binding.nowPlayingImg)
            binding.songNameNowPlaying.text = PlayerActivity.musicListPA[PlayerActivity.songPosition].title
            if(PlayerActivity.isPlaying) binding.playPauseBtnNowPlaying.setIconResource(R.drawable.pause_song_icon)
            else binding.playPauseBtnNowPlaying.setIconResource(R.drawable.play_icon)
        }
    }

    private fun playMusic(){
        PlayerActivity.musicService!!.mediaPlayer!!.start()
        binding.playPauseBtnNowPlaying.setIconResource(R.drawable.pause_song_icon)
        PlayerActivity.musicService!!.showNotification(R.drawable.pause_song_icon)
        PlayerActivity.isPlaying = true
    }

    private fun pauseMusic(){
        PlayerActivity.musicService!!.mediaPlayer!!.pause()
        binding.playPauseBtnNowPlaying.setIconResource(R.drawable.play_icon)
        PlayerActivity.musicService!!.showNotification(R.drawable.play_icon)
        PlayerActivity.isPlaying = false
    }

}