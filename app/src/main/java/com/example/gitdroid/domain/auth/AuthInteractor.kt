package com.example.gitdroid.domain.auth

interface AuthInteractor {
    fun checkAuthorized(): Boolean
    fun signInWithGithubProvider(email: String)
    fun logout()
}