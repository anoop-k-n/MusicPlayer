package com.example.musicplayer

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

//var checkDuplicates = ArrayList<String>()

class LibraryFragment : Fragment(R.layout.fragment_library) {
    private lateinit var adapter: AlbumAdapter
    private lateinit var recyclerView: RecyclerView


    companion object {
        lateinit var MusicListLibrary: ArrayList<Music>
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInitialize()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.albumRecyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = activity?.let { AlbumAdapter(it, MusicListLibrary) }!!
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : AlbumAdapter.onItemClickListener{
            override fun onItemCLick(position: Int) {
                val intent = Intent(context,PlayerActivity::class.java)
                intent.putExtra("album", MusicListLibrary[position].album)
                intent.putExtra("class","AlbumAdapter")

                startActivity(intent)

            }

        })
        

    }

    private fun dataInitialize() {
        MusicListLibrary = getAllAudio()
    }

    @SuppressLint("Range")
    private fun getAllAudio(): ArrayList<Music> {
        val tempList = ArrayList<Music>()
        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM_ID
        )
        val cursor = this.context?.contentResolver?.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null,
            MediaStore.Audio.Media.YEAR, null
        )
        if (cursor != null) {
            if (cursor.moveToFirst())
                do {
                    val titleCursor =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                            ?: "Unknown"
                    val idCursor =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                            ?: "Unknown"
                    val albumCursor =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                            ?: "Unknown"
                    val artistCursor =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                            ?: "Unknown"
                    val pathCursor =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val durationCursor =
                        cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    val albumIDCursor =
                        cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
                            .toString()
                    val uriCursor = Uri.parse("content://media/external/audio/albumart")
                    val artUriCursor = Uri.withAppendedPath(uriCursor, albumIDCursor).toString()
                    //making an object
                    val music = Music(
                        id = idCursor,
                        title = titleCursor,
                        album = albumCursor,
                        artist = artistCursor,
                        path = pathCursor,
                        duration = durationCursor,
                        artUri = artUriCursor
                    )
                    val file = File(music.path)

                    // adding albums to list so that duplicates can be filtered out and only unique ones can be shown
                    //val TAG = "Check Duplicates"

                    //if (music.album !in checkDuplicates) {
                        //Log.i(TAG, "${music.album} not in checkDuplicates")
                        //checkDuplicates.add(music.album)
                        if (file.exists()) {
                            tempList.add(music)
                        }
                    //}
                } while (cursor.moveToNext())
            cursor.close()
        }
        //checkDuplicates.clear()
        return tempList
    }
}