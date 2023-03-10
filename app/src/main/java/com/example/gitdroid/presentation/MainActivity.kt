package com.example.gitdroid.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gitdroid.R
import com.example.gitdroid.data.SessionManager
import com.example.gitdroid.databinding.ActivityMainBinding
import com.example.gitdroid.presentation.fragments.*
import com.example.gitdroid.presentation.misc.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity(), Navigation {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

//    private val projectsViewModel: ProjectsViewModel by viewModels {
//        (application as GitDroidApplication).appComponent.projectsViewModelFactory()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called with: savedInstanceState = $savedInstanceState")
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        initNavDrawer()

        if (checkAuthorized()) {
            val username = Firebase.auth.currentUser?.displayName.toString()
            Log.d(TAG, "Already authorized! Username in MainAct = $username")
            openHello(username)
        } else {
            Log.d(TAG, "Not yet authorized!")
            openAuth()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG, "onNewIntent() called with: intent = $intent")
        if (intent?.extras?.getBoolean("IS_AUTHORIZED") == true) {
            Log.d(TAG, "IS_AUTHORIZED = true")
            openHello(Firebase.auth.currentUser?.displayName.toString())
        }
    }

    private fun checkAuthorized(): Boolean {
        if (Firebase.auth.currentUser != null)
            return true
        return false
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
                    if (checkAuthorized()) {
                        openCodeSearch()
                    } else {
                        Toast.makeText(this@MainActivity,
                            "You are not authorized!",
                            Toast.LENGTH_LONG).show()
                    }
                }
                R.id.myProjItem -> {
                    Log.d(TAG, "Item 2 selected")
                    if (checkAuthorized()) {
                        openProjects()
                    } else {
                        Toast.makeText(this@MainActivity,
                            "You are not authorized!",
                            Toast.LENGTH_LONG).show()
                    }
                }
                R.id.logoutItem -> {
                    Log.d(TAG, "Logout selected")
                    if (checkAuthorized()) {
                        FirebaseAuth.getInstance().signOut()  // TODO повторяющийся код вынести
                        SessionManager(this@MainActivity).removeAuthToken()
                        Log.d(TAG, "Logout success")
                        openAuth()
                    } else {
                        Toast.makeText(this@MainActivity,
                            "You are not authorized!",
                            Toast.LENGTH_LONG).show()
                    }
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

    override fun openHello(userName: String) {
        launchFragment(HelloFragment.newInstance(userName))
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