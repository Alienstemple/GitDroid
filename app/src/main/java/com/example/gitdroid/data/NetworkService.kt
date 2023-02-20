package com.example.gitdroid.data

import android.util.Log
import com.example.gitdroid.models.domain.GHRepository

class NetworkService(private val githubApiService: GithubApiService) {
    suspend fun getReposByUser(name: String): List<GHRepository> {
        Log.d(TAG, "NetworkService called with: name = $name")
        githubApiService.getReposByUser(name)
        val resultList: List<GHRepository> = emptyList()
        return resultList
    }

    companion object {
        const val TAG = "NetwServiceLog"
    }
}