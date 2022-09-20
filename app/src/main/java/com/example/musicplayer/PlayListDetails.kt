package com.example.musicplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.musicplayer.databinding.ActivityPlayListDetailsBinding

class PlayListDetails : AppCompatActivity() {
    lateinit var binding: ActivityPlayListDetailsBinding
    companion object{
        var currentPlayListPos: Int = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayListDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currentPlayListPos = intent.extras?.get("index") as Int

    }
}