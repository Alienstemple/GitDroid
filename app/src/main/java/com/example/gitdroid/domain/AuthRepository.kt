package com.example.gitdroid.domain

interface AuthRepository {
    fun signInWithGithubProvider(email: String)
}