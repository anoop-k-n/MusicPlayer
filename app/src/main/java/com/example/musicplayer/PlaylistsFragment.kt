package com.example.musicplayer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.databinding.AddPlaylistDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

class PlaylistsFragment : Fragment(R.layout.fragment_playlists) {

    private lateinit var adapter: PlaylistAdapter
    private lateinit var recyclerView: RecyclerView

    companion object {
        var musicPlaylist: musicPlaylist = musicPlaylist()
    }

   // private lateinit var binding: PlaylistViewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        val addPlaylist =  view.findViewById<ExtendedFloatingActionButton>(R.id.AddPlaylistBtn)
        recyclerView = view.findViewById(R.id.PlaylistRecyclerView)

        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = activity?.let { PlaylistAdapter(it, musicPlaylist.ref) }!!
        recyclerView.adapter = adapter
        addPlaylist.setOnClickListener{
            context?.let { it1 -> customAlertDialog(it1) }
        }
    }

    private fun customAlertDialog(context: Context){
        val customDialog = LayoutInflater.from(context).inflate(R.layout.add_playlist_dialog,view?.findViewById(R.id.addPlaylistDialog),false)
        val binder = AddPlaylistDialogBinding.bind(customDialog)
        val builder = MaterialAlertDialogBuilder(context)
        builder.setView(customDialog)
            .setTitle("Playlist Details")
            .setPositiveButton("Add"){
                dialog, _ ->
                val playlistNameField = binder.PlaylistNameField.text
                if(playlistNameField != null)
                    if(playlistNameField.isNotEmpty()){
                        addPlaylist(playlistNameField.toString())
                    }
                dialog.dismiss()
            }.show()
    }

    private fun addPlaylist(name: String){
        var playlistExists = false
        for(i in musicPlaylist.ref){
            if(name == i.name){
                playlistExists = true
                break
            }
        }
        if(playlistExists) Toast.makeText(context,"Playlist exists!",Toast.LENGTH_SHORT).show()
        else{
            val tempPlaylist = Playlist()
            tempPlaylist.name = name
            tempPlaylist.playlist = ArrayList()

            val calender = Calendar.getInstance().time
            val sdf = SimpleDateFormat("dd MMM yyyy",Locale.ENGLISH)
            tempPlaylist.createdOn = sdf.format(calender)

            musicPlaylist.ref.add(tempPlaylist)
            adapter.refreshPlaylist()
        }
    }
}