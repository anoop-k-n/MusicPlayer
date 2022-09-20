package com.example.musicplayer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavouritesFragment : Fragment(R.layout.fragment_favourites) {
    private lateinit var adapter: FavoriteAdapter
    private lateinit var recyclerView: RecyclerView

    companion object{
        var favoriteSongs: ArrayList<Music> = ArrayList()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view,savedInstanceState)
        val layoutManager = GridLayoutManager(context,4)
        recyclerView = view.findViewById(R.id.FavoriteRecyclerView)

        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = activity?.let { FavoriteAdapter(it, favoriteSongs) }!!
        recyclerView.adapter = adapter

    }
}