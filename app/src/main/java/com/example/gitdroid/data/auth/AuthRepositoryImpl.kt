package com.example.gitdroid.data.auth

import android.content.Context
import android.util.Log
import com.example.gitdroid.domain.auth.AuthCallback
import com.example.gitdroid.domain.auth.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthCredential
import com.google.firebase.auth.OAuthProvider

class AuthRepositoryImpl(
    private val context: Context,
    private val auth: FirebaseAuth,
    private val provider: OAuthProvider.Builder
) : AuthRepository {

    override fun checkAuthorized(): Boolean {
        if (auth.currentUser != null)
            return true
        return false
    }

    override fun logout() {
        auth.signOut()
        SessionManager(context).removeAuthToken()
        Log.d(TAG, "Logout success")
    }

    override suspend fun signInWithGithubProvider(
        email: String,
        authCallbackInstance: AuthCallback,
    ) {
        Log.d(TAG,
            "signInWithGithubProvider() called with: email = $email")
        provider.addCustomParameter("login", email)
        provider.scopes = listOf("user:email")

        val result = authCallbackInstance.onRegister(auth, provider.build())
        Log.d(TAG, "Back to AuthRepo to save token")

        val accessToken = (result.credential as OAuthCredential).accessToken
        Log.d(TAG, "Access token = $accessToken")

        // Save access token in shared prefs
        SessionManager(context).saveAuthToken(accessToken.toString())
    }

    companion object {
        const val TAG = "AuthRepoLog"
    }
}