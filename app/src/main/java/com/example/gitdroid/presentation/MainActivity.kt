package com.example.gitdroid.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gitdroid.R
import com.example.gitdroid.databinding.ActivityMainBinding
import com.example.gitdroid.presentation.fragments.CodeSearchFragment
import com.example.gitdroid.presentation.fragments.HelloFragment
import com.example.gitdroid.presentation.fragments.ProjectsFragment
import com.example.gitdroid.presentation.misc.Navigation


class MainActivity : AppCompatActivity(), Navigation {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called with: savedInstanceState = $savedInstanceState")
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        initNavDrawer()

        if (savedInstanceState == null) {
            openHello()
        }
    }

    private fun logoutAuthFrag() {
        Log.d(TAG, "logoutAuthFrag() called")
        val intent = Intent(this, AuthActivity::class.java)
        intent.putExtra("LOGOUT", true)
        startActivity(intent)
//        finish()  // TODO
    }

    private fun initNavDrawer() = with(mainBinding) {
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
                    openCodeSearch()
                }
                R.id.myProjItem -> {
                    Log.d(TAG, "Item 2 selected")
                    openProjects()
                }
                R.id.logoutItem -> {
                    Log.d(TAG, "Logout selected")
                    logoutAuthFrag()
                }
                R.id.settingsItem -> {
                    Log.d(TAG, "Item 4 selected")
                }
            }
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun openProjects() {
        launchFragment(ProjectsFragment.newInstance())
    }

    override fun openCodeSearch() {
        launchFragment(CodeSearchFragment.newInstance())
    }

    override fun openHello() {
        launchFragment(HelloFragment.newInstance())
    }

    override fun logout() {
        logoutAuthFrag()
    }

    private fun launchFragment(fragment: Fragment) {
        Log.d(TAG, "Transact with name ${fragment::class.java.simpleName}")
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainActFragmContainer, fragment)
            .addToBackStack(fragment::class.java.simpleName)
            .commit()
    }

    companion object {
        const val TAG = "MainActLog"
    }
}