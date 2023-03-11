package com.example.gitdroid.domain.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.gitdroid.data.SessionManager
import com.example.gitdroid.presentation.MainActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthRepositoryImpl(
    private val context: Context,
) : AuthRepository {
    private lateinit var firebaseUser: FirebaseUser
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val provider = OAuthProvider.newBuilder(PROVIDER_ID)

    override fun checkAuthorized(): Boolean {
        if (auth.currentUser != null)
            return true
        return false
    }

    override fun logout() {
        auth.signOut()
        SessionManager(context).removeAuthToken()
        Log.d(MainActivity.TAG, "Logout success")
    }

    override fun signInWithGithubProvider(email: String) {

        provider.addCustomParameter("login", email)

        val scopes: ArrayList<String?> = object : ArrayList<String?>() {
            init {
                add("user:email")
            }
        }
        provider.scopes = scopes

        signInWithGithubProvider()
    }

    private fun signInWithGithubProvider() {

        // There's something already here! Finish the sign-in for your user.
        val pendingResultTask: Task<AuthResult>? = auth.pendingAuthResult
        if (pendingResultTask != null) {
            pendingResultTask
                .addOnSuccessListener {
                    Log.d(TAG, "User exist")
                }
                .addOnFailureListener {
                    Log.d(TAG, "Error : $it")
                }
        } else {

            auth.startActivityForSignInWithProvider( /* activity= */context as Activity,  // FIXME can't cast App context to Activity
                provider.build())
                .addOnSuccessListener {
                    // User is signed in.
                    // retrieve the current user
                    firebaseUser = auth.currentUser!!

                    val accessToken = (it.credential as OAuthCredential).accessToken
                    val idToken = (it.credential as OAuthCredential).idToken
                    Log.d(TAG, "Access token = $accessToken")
                    Log.d(TAG, "Id token = $idToken")

                    // Save access token in shared prefs
                    SessionManager(context).saveAuthToken(accessToken.toString())
                }
                .addOnFailureListener {
                    // Handle failure.
                    Log.d(TAG, "Error : $it")
                }
        }
    }

    companion object {
        const val TAG = "AuthRepoLog"
        const val PROVIDER_ID = "github.com"
    }
}