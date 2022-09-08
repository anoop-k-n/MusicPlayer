package com.example.musicplayer

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.musicplayer.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestRuntimePermission()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(LibraryFragment())



        binding.bottomNavigationView.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.library -> replaceFragment(LibraryFragment())
                R.id.playlists -> replaceFragment(PlaylistsFragment())
                R.id.favourites -> replaceFragment(FavouritesFragment())

                else -> {

                }
            }
            true
        }


    }

    // To replace the fragment when the bottomnavigationview is clicked
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

    // Inflate the main menu(toolbar)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // Action to be taken when clicked on
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_search -> Snackbar.make(
                findViewById(R.id.app_bar_search),
                "Search songs",
                Snackbar.LENGTH_LONG
            ).show()
            R.id.settings -> Snackbar.make(
                findViewById(R.id.settings),
                "Settings",
                Snackbar.LENGTH_LONG
            ).show()
            //TODO R.id.app_bar_search -> search songs
            //TODO R.id.settings -> go to settings
        }
        return true
    }

    // Request for storage permisssion
    private fun requestRuntimePermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
          //  if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) super.onRequestPermissionsResult(
            //    requestCode,permissions,grantResults)
            //Snackbar.make(findViewById(R.id.mainAppBar),"Permission Granted",Snackbar.LENGTH_SHORT).show()
        }
    }
}