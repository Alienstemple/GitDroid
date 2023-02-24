package com.example.gitdroid.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.gitdroid.R
import com.example.gitdroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        toggle = ActionBarDrawerToggle(this,
            mainBinding.drawerLayout,
            R.string.open_nav_drawer,
            R.string.close_nav_drawer)
        mainBinding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mainBinding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item1 -> {
                    Log.d(TAG, "Item 1 selected")
                }
                R.id.item2 -> {
                    Log.d(TAG, "Item 2 selected")
                }
            }
            true
        }

//        startApp()

    }

    private fun startApp() {
        intent = Intent(this, StartAppActivity::class.java)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val TAG = "MainActLog"
    }
}