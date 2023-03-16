package com.example.gitdroid.data.auth

import android.util.Log
import com.example.gitdroid.domain.auth.AuthCallback
import com.example.gitdroid.domain.auth.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthCredential
import com.google.firebase.auth.OAuthProvider

/**
 * Имплементирует интерфейс [AuthRepository]
 * Отвечает за авторизацию, проверку авторизации и логаут
 * @constructor Принимает экземпляры [FirebaseAuth], [OAuthProvider.Builder], [SessionManager]
 */
class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val provider: OAuthProvider.Builder,
    private val sessionManager: SessionManager
) : AuthRepository {

    override fun checkAuthorized(): Boolean {
        if (auth.currentUser != null)
            return true
        return false
    }

    override fun logout() {
        auth.signOut()
        sessionManager.removeAuthToken()
        Log.d(TAG, "Logout success")
    }

    override suspend fun signInWithGithubProvider(
        email: String,
        authCallbackInstance: AuthCallback,
    ) {
        provider.addCustomParameter("login", email)
        provider.scopes = listOf("user:email")

        val result = authCallbackInstance.onRegister(auth, provider.build())

        val accessToken = (result.credential as OAuthCredential).accessToken
        Log.d(TAG, "Access token = $accessToken")

        // Save access token in shared prefs
        sessionManager.saveAuthToken(accessToken.toString())
    }

    companion object {
        const val TAG = "AuthRepoLog"
    }
}