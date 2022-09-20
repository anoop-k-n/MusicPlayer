package com.example.musicplayer

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.musicplayer.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity(), ServiceConnection, MediaPlayer.OnCompletionListener {

    companion object{
        lateinit var musicListPA: ArrayList<Music>
        var songPosition: Int = 0
        var isPlaying: Boolean = false
        var musicService: MusicService? = null
        @SuppressLint("StaticFieldLeak")
        lateinit var binding: ActivityPlayerBinding
        var nowPlayingID: String = ""
        var isFavorite: Boolean = false
        var favIndex: Int = -1
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeLayout()

        binding.backBtn.setOnClickListener{ finish() }

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

        binding.seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser) musicService!!.mediaPlayer!!.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })

        binding.favoriteBtnInPlayer.setOnClickListener {
            if(isFavorite){
                binding.favoriteBtnInPlayer.setImageResource(R.drawable.favorite_empty_icon)
                isFavorite = false
                FavouritesFragment.favoriteSongs.removeAt(favIndex)
            }
            else{
                binding.favoriteBtnInPlayer.setImageResource(R.drawable.ic_favourites)
                isFavorite = true
                FavouritesFragment.favoriteSongs.add(musicListPA[songPosition])
            }
        }
    }


    private fun setLayout(){
        favIndex = favoriteChecker(musicListPA[songPosition].id)
        if(isFavorite) binding.favoriteBtnInPlayer.setImageResource(R.drawable.ic_favourites)
        else binding.favoriteBtnInPlayer.setImageResource(R.drawable.favorite_empty_icon)
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
            // change notification every time a mediaplayer object is created
            musicService!!.showNotification(R.drawable.pause_song_icon)

            binding.progressTime.text = formatDuration(musicService!!.mediaPlayer!!.currentPosition.toLong())
            binding.timeTotal.text = formatDuration(musicService!!.mediaPlayer!!.duration.toLong())
            binding.seekBar.progress = 0
            binding.seekBar.max = musicService!!.mediaPlayer!!.duration
            musicService!!.mediaPlayer!!.setOnCompletionListener(this)
            nowPlayingID = musicListPA[songPosition].id
        }catch(e: Exception){return}
    }


    private fun initializeLayout(){
        songPosition = intent.getIntExtra("index",0)
        when(intent.getStringExtra("class")){
            "NowPlaying" ->{
                setLayout()
                binding.progressTime.text = formatDuration(musicService!!.mediaPlayer!!.currentPosition.toLong())
                binding.timeTotal.text = formatDuration(musicService!!.mediaPlayer!!.duration.toLong())
                binding.seekBar.progress = musicService!!.mediaPlayer!!.currentPosition
                binding.seekBar.max = musicService!!.mediaPlayer!!.duration
                if(isPlaying) binding.playPauseBtn.setIconResource(R.drawable.pause_song_icon)
                else binding.playPauseBtn.setIconResource(R.drawable.play_icon)

            }
            "AlbumAdapter" ->{
                // start the service
                val intent = Intent(this,MusicService::class.java)
                bindService(intent,this, BIND_AUTO_CREATE)
                startService(intent)

                musicListPA = ArrayList()
                musicListPA.addAll(LibraryFragment.MusicListLibrary)
                setLayout()
            }
        }
    }

    private fun playMusic(){
        binding.playPauseBtn.setIconResource(R.drawable.pause_song_icon)
        musicService!!.showNotification(R.drawable.pause_song_icon)
        isPlaying = true
        musicService!!.mediaPlayer!!.start()

    }

    private fun pauseMusic(){
        binding.playPauseBtn.setIconResource(R.drawable.play_icon)
        musicService!!.showNotification(R.drawable.play_icon)
        isPlaying = false
        musicService!!.mediaPlayer!!.pause()
    }
    private fun changeSong(increment:Boolean){
        setSongPosition(increment)
        setLayout()
        createMediaPlayer()
    }


    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as MusicService.MyBinder
        musicService = binder.currentService()
        createMediaPlayer()
        musicService!!.seekBarSetter()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        musicService = null
    }

    override fun onCompletion(mp: MediaPlayer?) {
        setSongPosition(increment = true)
        createMediaPlayer()
        try{
            setLayout()
        }catch (e: Exception) {return}

    }
}