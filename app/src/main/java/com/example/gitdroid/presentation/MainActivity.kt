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
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
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
                    Toast.makeText(this@MainActivity, "Enter your github id", Toast.LENGTH_LONG)
                        .show()
                } else {
                    signInWithGithubProvider()
                }
            }
        }

    }

    private fun signInWithGithubProvider() {

        // There's something already here! Finish the sign-in for your user.
        val pendingResultTask: Task<AuthResult>? = auth.pendingAuthResult
        if (pendingResultTask != null) {
            pendingResultTask
                .addOnSuccessListener {
                    // User is signed in.
                    Toast.makeText(this, "User exist", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    // Handle failure.
                    Toast.makeText(this, "Error : $it", Toast.LENGTH_LONG).show()
                    Log.d(TAG, "Error : $it")
                }
        } else {

            auth.startActivityForSignInWithProvider( /* activity= */this, provider.build())
                .addOnSuccessListener(
                    OnSuccessListener<AuthResult?> {
                        // User is signed in.
                        // retrieve the current user
                        firebaseUser = auth.currentUser!!

                        // navigate to HomePageActivity after successful login
                        val intent = Intent(this, HomePageActivity::class.java)

                        // send github user name from MainActivity to HomePageActivity
                        intent.putExtra("githubUserName", firebaseUser.displayName)
                        this.startActivity(intent)
                        Toast.makeText(this, "Login Successfully", Toast.LENGTH_LONG).show()

                    })
                .addOnFailureListener(
                    OnFailureListener {
                        // Handle failure.
                        Toast.makeText(this, "Error : $it", Toast.LENGTH_LONG).show()
                        Log.d(TAG, "Error : $it")
                    })
        }
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