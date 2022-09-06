package com.example.musicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarMenu
import com.google.android.material.navigation.NavigationBarView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val LibraryFragment = LibraryFragment()
        val PlaylistsFragment = PlaylistsFragment()
        val SettingsFragment = SettingsFragment()

        setCurrentFragment(LibraryFragment)

        NavigationBarView.OnItemSelectedListener {
            when(it.itemId){
                R.id.library -> setCurrentFragment(LibraryFragment)
                R.id.playlists -> setCurrentFragment(PlaylistsFragment)
                R.id.settings -> setCurrentFragment(SettingsFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            //addToBackStack(null)
            commit()
        }
}