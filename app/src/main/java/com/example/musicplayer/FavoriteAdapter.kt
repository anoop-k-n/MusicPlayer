package com.example.musicplayer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.databinding.FavoriteViewBinding

class FavoriteAdapter(private val context: Context, private val musicList : ArrayList<String>) : RecyclerView.Adapter<FavoriteAdapter.MyHolder>() {

    class MyHolder(binding: FavoriteViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.SongImgInFavorites
        val name = binding.SongTitleInFavorites
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyHolder {
        return MyHolder(FavoriteViewBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.name.text = musicList[position]
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    fun sendIntent(ref: String,pos: Int){
        val intent = Intent(context,PlayerActivity::class.java)
        intent.putExtra("index",pos)
        intent.putExtra("ref",ref)
        ContextCompat.startActivity(context,intent,null)
    }

}
