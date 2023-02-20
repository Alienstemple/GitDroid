package com.example.gitdroid.domain

import android.util.Log
import com.example.gitdroid.models.domain.GHRepository

class GithubInteractorImpl (private val networkRepository: NetworkRepository): GithubInteractor {
    override suspend fun getReposByUser(name: String): List<GHRepository> {
        Log.d(TAG, "getReposByUser() called with: name = $name")
        return networkRepository.getReposByUser(name)
    }

    companion object {
        const val TAG = "GHInteractLog"
    }
}