package com.example.gitdroid.data

import android.util.Log
import com.example.gitdroid.data.converters.GHRepositoryConverter
import com.example.gitdroid.domain.NetworkRepository
import com.example.gitdroid.models.domain.GHRepository
import com.example.gitdroid.models.domain.Issue

class NetworkRepositoryImpl(private val networkService: NetworkService): NetworkRepository {
    override suspend fun getReposByUser(name: String): List<GHRepository> {
        Log.d(TAG, "getReposByUser() called with: name = $name")
        val resultList = networkService.getReposByUser(name)
        return resultList
    }

    override suspend fun getIssuesByUserAndRepository(name: String, repo: String): List<Issue> {
        Log.d(TAG, "getIssuesByUserAndRepository() called with: name = $name, repo = $repo")
        val resultList = networkService.getIssuesByUserAndRepository(name, repo)
        return resultList
    }

    companion object {
        const val TAG = "NetworkRepoLog"
    }
}