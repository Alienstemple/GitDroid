package com.example.gitdroid.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.gitdroid.R
import com.example.gitdroid.databinding.ActivityMainBinding
import com.example.gitdroid.presentation.fragments.AuthFragment
import com.example.gitdroid.presentation.fragments.FindReposByUserFragment
import com.example.gitdroid.presentation.misc.Navigation

class MainActivity : AppCompatActivity(), Navigation {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        with(mainBinding) {
            // Action Drawer Menu
            toggle = ActionBarDrawerToggle(this@MainActivity,
                drawerLayout,
                R.string.open_nav_drawer,
                R.string.close_nav_drawer)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            navView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.searchItem -> {
                        Log.d(TAG, "Item 1 selected")
                    }
                    R.id.myProjItem -> {
                        Log.d(TAG, "Item 2 selected")
                    }
                    R.id.repoAndIssItem -> {
                        Log.d(TAG, "Item 3 selected")
                    }
                    R.id.settingsItem -> {
                        Log.d(TAG, "Item 4 selected")
                    }
                }
                true
            }
        }

        // Launch authorization
        openAuth()
    }

//    private fun startApp() {
//        intent = Intent(this, StartAppActivity::class.java)
//        startActivity(intent)
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun openAuth() {
        launchFragment(AuthFragment.newInstance())
    }

    override fun openFindReposByUser() {
        launchFragment(FindReposByUserFragment.newInstance())
    }

    private fun launchFragment(fragment: Fragment) {
        Log.d(StartAppActivity.TAG, "Transact with name ${fragment::class.java.simpleName}")
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainActFragmContainer, fragment)
            .addToBackStack(fragment::class.java.simpleName)
            .commit()
    }

    companion object {
        const val TAG = "MainActLog"
    }
}