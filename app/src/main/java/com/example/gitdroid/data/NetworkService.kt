package com.example.gitdroid.data

import android.util.Log
import com.example.gitdroid.models.domain.GHRepository

class NetworkService(private val githubApiService: GithubApiService) {
    suspend fun getReposByUser(name: String): List<GHRepository> {
        Log.d(TAG, "NetworkService called with: name = $name")

        githubApiService.getReposByUser(name).body()?.let {
            Log.d(TAG, "From network we have: $it")
            return it
        }
        return emptyList()
    }

    companion object {
        const val TAG = "NetwServiceLog"
    }
}