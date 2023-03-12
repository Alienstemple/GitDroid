package com.example.gitdroid.domain.auth

interface AuthRepository {
    fun checkAuthorized(): Boolean
    fun signInWithGithubProvider(email: String, authCallbackInstance: AuthCallback)
    fun logout()
}