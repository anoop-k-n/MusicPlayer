package com.example.musicplayer

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.musicplayer.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity(), ServiceConnection {

    companion object{
        lateinit var musicListPA: ArrayList<Music>
        var songPosition: Int = 0
        var isPlaying: Boolean = false
        var musicService: MusicService? = null
    }

    private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // start the service
        val intent = Intent(this,MusicService::class.java)
        bindService(intent,this, BIND_AUTO_CREATE)
        startService(intent)

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
            if(musicService!!.mediaPlayer == null) musicService!!.mediaPlayer = MediaPlayer()
            musicService!!.mediaPlayer!!.reset()
            musicService!!.mediaPlayer!!.setDataSource(musicListPA[songPosition].path)
            musicService!!.mediaPlayer!!.prepare()
            musicService!!.mediaPlayer!!.start()
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
            }
        }
    }

    private fun playMusic(){
        binding.playPauseBtn.setIconResource(R.drawable.pause_song_icon)
        isPlaying = true
        musicService!!.mediaPlayer!!.start()

    }

    private fun pauseMusic(){
        binding.playPauseBtn.setIconResource(R.drawable.play_icon)
        isPlaying = false
        musicService!!.mediaPlayer!!.pause()
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

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as MusicService.MyBinder
        musicService = binder.currentService()
        createMediaPlayer()
        musicService!!.showNotification()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        musicService = null
    }
}