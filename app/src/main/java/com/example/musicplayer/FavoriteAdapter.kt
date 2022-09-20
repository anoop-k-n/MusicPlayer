package com.example.musicplayer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.musicplayer.databinding.FavoriteViewBinding

class FavoriteAdapter(private val context: Context, private val musicList : ArrayList<Music>) : RecyclerView.Adapter<FavoriteAdapter.MyHolder>() {

    private lateinit var mListener: FavoriteAdapter.onItemClickListener


    interface onItemClickListener{
        fun onItemCLick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    class MyHolder(binding: FavoriteViewBinding,listener: onItemClickListener) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.SongImgInFavorites
        val name = binding.SongTitleInFavorites
        val root = binding.root

        init{
            itemView.setOnClickListener {
                listener.onItemCLick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyHolder {
        return MyHolder(FavoriteViewBinding.inflate(LayoutInflater.from(context),parent,false),mListener)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.name.text = musicList[position].title
        Glide.with(context)
            .load(musicList[position].artUri)
            .apply(RequestOptions().placeholder(R.drawable.music_player_icon_splash_screen).centerCrop())
            .into(holder.image)
        holder.root.setOnClickListener{
            val intent = Intent(context,PlayerActivity::class.java)
            intent.putExtra("index",position)
            intent.putExtra("ref","FavoriteAdapter")
            ContextCompat.startActivity(context,intent,null)
        }
    }

    override fun getItemCount(): Int {
        return musicList.size
    }


}
