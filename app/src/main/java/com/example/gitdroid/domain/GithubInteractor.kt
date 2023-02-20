package com.example.gitdroid.domain

import com.example.gitdroid.models.domain.GHRepository

interface GithubInteractor {
    suspend fun getReposByUser(name: String): List<GHRepository>
}