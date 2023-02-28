package com.example.gitdroid.presentation

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gitdroid.R
import com.example.gitdroid.databinding.ActivityMainBinding
import com.example.gitdroid.presentation.fragments.FindReposByUserFragment
import com.example.gitdroid.presentation.misc.Navigation
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.OAuthCredential
import com.google.firebase.auth.OAuthProvider


class MainActivity : AppCompatActivity(), Navigation {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var firebaseUser: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private val provider = OAuthProvider.newBuilder("github.com")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        initNavDrawer()

        authWithFirebase()
    }

    private fun authWithFirebase() = with(mainBinding) {
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

                        val accessToken = (it.credential as OAuthCredential).accessToken
                        val idToken = (it.credential as OAuthCredential).idToken
                        Log.d(TAG, "Access token = $accessToken")
                        Log.d(TAG, "Id token = $idToken")

                        // Launch hello fragment

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun openFindReposByUser() {
        launchFragment(FindReposByUserFragment.newInstance())
    }

    private fun launchFragment(fragment: Fragment) {
        Log.d(StartAppActivity.TAG, "Transact with name ${fragment::class.java.simpleName}")
        supportFragmentManager.beginTransaction()
            .replace(R.id.search_repos_by_user_frag_container, fragment)
            .addToBackStack(fragment::class.java.simpleName)
            .commit()
    }

    companion object {
        const val TAG = "MainActLog"
    }
}