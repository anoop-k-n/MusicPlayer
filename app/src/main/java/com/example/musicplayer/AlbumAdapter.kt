package com.example.musicplayer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.musicplayer.databinding.AlbumViewBinding
import com.google.android.material.imageview.ShapeableImageView


class AlbumAdapter(private val context: Context, private val musicList : ArrayList<Music>) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    private lateinit var mListener: onItemClickListener
    lateinit var binding: AlbumViewBinding
    interface onItemClickListener{
        fun onItemCLick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    class AlbumViewHolder(itemView : View,listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val image : ShapeableImageView = itemView.findViewById(R.id.albumInLibraryImage)
        val album : TextView = itemView.findViewById(R.id.albumInLibrary)
        val songTitle : TextView = itemView.findViewById(R.id.songTitleInLibrary)

        init{
                itemView.setOnClickListener {
                listener.onItemCLick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumAdapter.AlbumViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.library_fragment_view,parent,false)

        return AlbumViewHolder(itemView,mListener)
            }

    override fun onBindViewHolder(holder: AlbumAdapter.AlbumViewHolder, position: Int) {
        holder.album.text = musicList[position].album
        Glide.with(context)
            .load(musicList[position].artUri)
                .apply(RequestOptions().placeholder(R.drawable.music_player_icon_splash_screen).centerCrop())
                .into(holder.image)
        holder.songTitle.text = musicList[position].title
        /*holder.itemView.setOnClickListener{
                // if same song is clicked
                if(musicList[position].id == PlayerActivity.nowPlayingID)
                    sendIntent(ref = "NowPlaying", pos = PlayerActivity.songPosition)
                else sendIntent(ref="AlbumAdapter",pos = position)
            }*/
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






