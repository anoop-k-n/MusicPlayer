package com.example.musicplayer

import android.app.Application
import android.os.Build

class ApplicationClass: Application() {
    override fun onCreate() {
        super.onCreate()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

        }
    }
}