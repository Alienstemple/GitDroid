package com.example.gitdroid.presentation

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import com.example.gitdroid.R
import com.example.gitdroid.databinding.ActivityMainBinding
import com.example.gitdroid.models.domain.GHRepository
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResultItem
import com.example.gitdroid.presentation.fragments.*
import com.example.gitdroid.presentation.misc.Navigation
import com.example.gitdroid.presentation.misc.ProjectItemClickListener
import com.example.gitdroid.presentation.misc.SearchResultItemClickListener


class MainActivity : AppCompatActivity(), Navigation {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        initNavDrawer()

        openAuth()
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

    override fun openAuth() {
        launchFragment(AuthFragment.newInstance())
    }

    override fun openHello(userName: String, avatarUrl: String) {
        launchFragment(HelloFragment.newInstance(userName, avatarUrl))
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