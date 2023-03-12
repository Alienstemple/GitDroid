package com.example.gitdroid.data.auth

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.gitdroid.domain.auth.AuthCallback
import com.example.gitdroid.domain.auth.AuthRepository
import com.example.gitdroid.presentation.MainActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*

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

    override suspend fun signInWithGithubProvider(email: String, authCallbackInstance: AuthCallback) {
        Log.d(TAG,
            "signInWithGithubProvider() called with: email = $email")
        provider.addCustomParameter("login", email)
        provider.scopes = listOf("user:email")

        // There's something already here! Finish the sign-in for your user.
        val pendingResultTask: Task<AuthResult>? = auth.pendingAuthResult // TODO remove
        if (pendingResultTask != null) {
            pendingResultTask
                .addOnSuccessListener {
                    Log.d(TAG, "User exist")
                }
                .addOnFailureListener {
                    Log.d(TAG, "Error : $it")
                }
        } else {

            val result = authCallbackInstance.onRegister(auth, provider.build())
            Log.d(TAG, "Back to AuthRepo to save token")
            // User is signed in.
            // retrieve the current user
            firebaseUser = auth.currentUser!!

            val accessToken = (result.credential as OAuthCredential).accessToken
            val idToken = (result.credential as OAuthCredential).idToken
            Log.d(TAG, "Access token = $accessToken")
            Log.d(TAG, "Id token = $idToken")

            // Save access token in shared prefs
            SessionManager(context).saveAuthToken(accessToken.toString())

//                .addOnSuccessListener {
//                    Log.d(TAG, "In on success listener")
//                    // User is signed in.
//                    // retrieve the current user
//                    firebaseUser = auth.currentUser!!
//
//                    val accessToken = (it.credential as OAuthCredential).accessToken
//                    val idToken = (it.credential as OAuthCredential).idToken
//                    Log.d(TAG, "Access token = $accessToken")
//                    Log.d(TAG, "Id token = $idToken")
//
//                    // Save access token in shared prefs
//                    SessionManager(context).saveAuthToken(accessToken.toString())
//                }
//                .addOnFailureListener {
////                     Handle failure.
//                    Log.d(TAG, "Error : $it")
//                }
        }
    }

    companion object {
        const val TAG = "AuthRepoLog"
        const val PROVIDER_ID = "github.com"
    }
}