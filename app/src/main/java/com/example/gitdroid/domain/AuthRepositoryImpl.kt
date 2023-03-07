package com.example.gitdroid.domain

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import com.example.gitdroid.data.SessionManager
import com.example.gitdroid.presentation.MainActivity
import com.example.gitdroid.presentation.fragments.AuthFragment
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthRepositoryImpl(
    private val context: Context,
    private val fragmentActivity: FragmentActivity,
) : AuthRepository {
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private val provider = OAuthProvider.newBuilder("github.com")

    override fun signInWithGithubProvider(email: String) {

        auth = FirebaseAuth.getInstance()

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
                    Log.d(AuthFragment.TAG, "User exist")
                }
                .addOnFailureListener {
                    Log.d(AuthFragment.TAG, "Error : $it")
                }
        } else {

            auth.startActivityForSignInWithProvider( /* activity= */fragmentActivity,
                provider.build())
                .addOnSuccessListener(
                    OnSuccessListener<AuthResult?> {
                        // User is signed in.
                        // retrieve the current user
                        firebaseUser = auth.currentUser!!

                        val accessToken = (it.credential as OAuthCredential).accessToken
                        val idToken = (it.credential as OAuthCredential).idToken
                        Log.d(MainActivity.TAG, "Access token = $accessToken")
                        Log.d(MainActivity.TAG, "Id token = $idToken")

                        // Save access token in shared prefs
                        SessionManager(context).saveAuthToken(accessToken.toString())

                        val username = auth.currentUser?.displayName.toString()  // FIXME null
                        Log.d(AuthFragment.TAG, "Username in AuthFrag = $username")

                        Log.d(AuthFragment.TAG, "Before starting MainActivity")
                        val intent = Intent(context, MainActivity::class.java)
                        intent.putExtra("IS_AUTHORIZED", true)
                        context.startActivity(intent)

                    })
                .addOnFailureListener(
                    OnFailureListener {
                        // Handle failure.
                        Toast.makeText(context, "Error : $it", Toast.LENGTH_LONG).show()
                        Log.d(MainActivity.TAG, "Error : $it")
                    })
        }
    }
}