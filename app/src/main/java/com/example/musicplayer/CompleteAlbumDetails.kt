package com.example.musicplayer

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CompleteAlbumDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_album_details)

        // Get the name of the album clicked in the main activity
        val albumNeeded = intent.getStringExtra("key")
        val textAlbum: TextView = findViewById<TextView>(R.id.AlbumName)
        textAlbum.text = albumNeeded

        // List all songs in that album


    }

}