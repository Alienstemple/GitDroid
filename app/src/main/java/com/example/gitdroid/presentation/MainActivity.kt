package com.example.gitdroid.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.gitdroid.R
import com.example.gitdroid.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.OAuthProvider

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var firebaseUser: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private val provider = OAuthProvider.newBuilder("github.com")

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

            // Authorization
            auth = FirebaseAuth.getInstance()

            provider.addCustomParameter("login", enterEmailEditText.text.toString())

            val scopes: ArrayList<String?> = object : ArrayList<String?>() {
                init {
                    add("user:email")
                }
            }
            provider.scopes = scopes

            authBtn.setOnClickListener {
                if (TextUtils.isEmpty(mainBinding.enterEmailEditText.text.toString())) {
                    Toast.makeText(this@MainActivity, "Enter your github id", Toast.LENGTH_LONG).show()
                } else {
                    signInWithGithubProvider()
                }
            }
        }

    }

    private fun signInWithGithubProvider() {

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