package com.example.musicplayer

import android.media.MediaMetadataRetriever
import java.util.concurrent.TimeUnit

data class Music(val id:String, val title:String,val album:String, val artist:String, val duration: Long = 0,
                 val path: String, val artUri: String)

class Playlist{
    lateinit var name: String
    lateinit var playlist: ArrayList<Music>
    lateinit var createdOn: String
}

class musicPlaylist{
    var ref: ArrayList<Playlist> = ArrayList()

}
fun formatDuration(duration: Long): String{
    val min = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
    val sec = (TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS) -
            min*TimeUnit.SECONDS.convert(1,TimeUnit.MINUTES))

    return String.format("%02d:%02d",min,sec)
}

fun getImageArtForNotification(path: String): ByteArray? {
    val retriever = MediaMetadataRetriever()
    retriever.setDataSource(path)
    return retriever.embeddedPicture
}

fun setSongPosition(increment: Boolean){
    if(increment){
        // max boundary condition, start looping around if that is the case
        if(PlayerActivity.musicListPA.size - 1 == PlayerActivity.songPosition)
            PlayerActivity.songPosition = 0
        else ++PlayerActivity.songPosition
    }
    // min boundary condition, start looping from backward if so
    else{
        if(PlayerActivity.songPosition == 0)
            PlayerActivity.songPosition = PlayerActivity.musicListPA.size - 1
        else --PlayerActivity.songPosition
    }
}


fun favoriteChecker(id: String): Int{
    PlayerActivity.isFavorite = false
    FavouritesFragment.favoriteSongs.forEachIndexed{index, music ->
        if(id == music.id){
            PlayerActivity.isFavorite = true
            return index
        }
    }
    return -1
}