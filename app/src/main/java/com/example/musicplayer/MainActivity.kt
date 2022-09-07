package com.example.musicplayer

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.musicplayer.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(LibraryFragment())
        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){
                R.id.library -> replaceFragment(LibraryFragment())
                R.id.playlists -> replaceFragment(PlaylistsFragment())
                R.id.favourites -> replaceFragment(FavouritesFragment())

                else->{

                }
        }
        true
        }


    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.app_bar_search -> Snackbar.make(findViewById(R.id.app_bar_search),"Search songs",Snackbar.LENGTH_LONG).show()
            R.id.settings -> Snackbar.make(findViewById(R.id.settings),"Settings",Snackbar.LENGTH_LONG).show()
            //TODO R.id.app_bar_search -> search songs
            //TODO R.id.settings -> go to settings
        }
        return true
    }
}