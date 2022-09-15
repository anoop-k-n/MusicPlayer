package com.example.musicplayer

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.musicplayer.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {

    companion object{
        lateinit var musicListPA: ArrayList<Music>
        var songPosition: Int = 0
        var mediaPlayer: MediaPlayer? = null
        var isPlaying: Boolean = false
    }

    private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeLayout()
        binding.playPauseBtn.setOnClickListener{
            if(isPlaying) pauseMusic()
            else playMusic()
        }
        binding.previousBtn.setOnClickListener{
            changeSong(increment = false)
        }

        binding.nextBtn.setOnClickListener{
            changeSong(increment = true)
        }
    }


    private fun setLayout(){
        Glide.with(this)
            .load(musicListPA[songPosition].artUri)
            .apply(RequestOptions().placeholder(R.drawable.music_player_icon_splash_screen).centerCrop())
            .into(binding.SongImageInPlayer)
        binding.SongTitleInPlayer.text = musicListPA[songPosition].title
    }


    private fun createMediaPlayer(){
        try {
            if(mediaPlayer == null) mediaPlayer = MediaPlayer()
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(musicListPA[songPosition].path)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
            isPlaying = true
            binding.playPauseBtn.setIconResource(R.drawable.pause_song_icon)
        }catch(e: Exception){return}
    }


    private fun initializeLayout(){
        songPosition = intent.getIntExtra("index",0)
        when(intent.getStringExtra("class")){
            "AlbumAdapter" ->{
                musicListPA = ArrayList()
                musicListPA.addAll(LibraryFragment.MusicListLibrary)
                setLayout()
                createMediaPlayer()
            }
        }
    }

    private fun playMusic(){
        binding.playPauseBtn.setIconResource(R.drawable.pause_song_icon)
        isPlaying = true
        mediaPlayer!!.start()

    }

    private fun pauseMusic(){
        binding.playPauseBtn.setIconResource(R.drawable.play_icon)
        isPlaying = false
        mediaPlayer!!.pause()
    }

    private fun changeSong(increment:Boolean){
        setSongPosition(increment)
        setLayout()
        createMediaPlayer()
    }


    private fun setSongPosition(increment: Boolean){
        if(increment){
            // max boundary condition, start looping around if that is the case
            if(musicListPA.size - 1 == songPosition)
                songPosition = 0
            else ++songPosition
        }
        // min boundary condition, start looping from backward if so
        else{
            if(songPosition == 0)
                songPosition = musicListPA.size - 1
            else --songPosition
        }
    }
}