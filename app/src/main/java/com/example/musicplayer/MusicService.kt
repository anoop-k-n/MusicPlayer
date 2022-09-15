package com.example.musicplayer

import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat

class MusicService: Service() {
    private var myBinder = MyBinder()
    var mediaPlayer: MediaPlayer? = null
    private lateinit var mediaSession: MediaSessionCompat

    override fun onBind(p0: Intent?): IBinder {
        mediaSession = MediaSessionCompat(baseContext,"My Music")
        return myBinder
    }
    inner class MyBinder:Binder(){
        fun currentService(): MusicService {
            return this@MusicService
        }
    }

    fun showNotification(){
        val notification = NotificationCompat.Builder(baseContext,ApplicationClass.CHANNEL_ID)
            .setContentTitle(PlayerActivity.musicListPA[PlayerActivity.songPosition].title)
            .setContentTitle(PlayerActivity.musicListPA[PlayerActivity.songPosition].artist)
            .setSmallIcon(R.drawable.ic_playlists)
            .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.music_player_icon_splash_screen))
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(mediaSession.sessionToken))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOnlyAlertOnce(true)
            .addAction(R.drawable.previous_song_icon, "Previous",null)
            .addAction(R.drawable.play_icon, "Play",null)
            .addAction(R.drawable.next_song_icon, "Next",null)
            .build()

       // startForeground(10,notification)
    }
}