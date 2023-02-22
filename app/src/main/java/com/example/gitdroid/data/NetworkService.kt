package com.example.gitdroid.data

import android.util.Log
import com.example.gitdroid.models.domain.GHRepository
import com.example.gitdroid.models.domain.Issue

class NetworkService(private val githubApiService: GithubApiService) {
    suspend fun getReposByUser(name: String): List<GHRepository> {
        Log.d(TAG, "NetworkService called with: name = $name")

        githubApiService.getReposByUser(name).body()?.let {
            Log.d(TAG, "From network we have: $it")
            return it
        }
        return emptyList()
    }

    suspend fun getIssuesByUserAndRepository(name: String, repo: String): List<Issue> {
        Log.d(TAG, "NetworkService called with: name = $name, repo = $repo")

        githubApiService.getIssuesByUserAndRepository(name, repo).body()?.let {
            Log.d(TAG, "From network we have: $it")
            return it
        }
        return emptyList()
    }

    companion object {
        const val TAG = "NetwServiceLog"
    }
}