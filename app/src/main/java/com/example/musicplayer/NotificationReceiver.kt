package com.example.musicplayer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action){
            ApplicationClass.PREVIOUS -> Toast.makeText(context,"Previous clicked",Toast.LENGTH_SHORT).show()
            ApplicationClass.PLAY -> Toast.makeText(context,"Play clicked",Toast.LENGTH_SHORT).show()
            ApplicationClass.NEXT -> Toast.makeText(context,"Next clicked",Toast.LENGTH_SHORT).show()
        }
    }
}