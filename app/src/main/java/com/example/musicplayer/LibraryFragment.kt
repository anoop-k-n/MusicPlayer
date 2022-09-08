package com.example.musicplayer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class LibraryFragment : Fragment(R.layout.fragment_library) {
    private lateinit var adapter: AlbumAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var albumArrayList: ArrayList<album>

    lateinit var imageId: Array<Int>
    lateinit var albumName: Array<String>
    lateinit var albums: Array<Int>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInitialize()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.albumRecyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.hasFixedSize()
        //todo check this part of the months before. Invetigate the hasFixedSixe()
        adapter = AlbumAdapter(albumArrayList)
        recyclerView.adapter = adapter
    }

    private fun dataInitialize(){
        albumArrayList = arrayListOf<album>()
        imageId = arrayOf(
            R.drawable.pixelated_oblivion,
            R.drawable.Providence,
            R.drawable.Fall,
            R.drawable.ronin,
            R.drawable.starboy
        )

        albumName = arrayOf(
            getString(R.string.pixel),
            getString(R.string.providence),
            getString(R.string.fall),
            getString(R.string.ronin),
            getString(R.string.starboy)
        )

        for(i in imageId.indices){
            val album = album(imageId[i],albumName[i])
            albumArrayList.add(album)
        }
    }
}