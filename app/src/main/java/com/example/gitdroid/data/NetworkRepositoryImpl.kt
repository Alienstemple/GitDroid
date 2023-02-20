package com.example.gitdroid.data

import android.util.Log
import com.example.gitdroid.data.converters.GHRepositoryConverter
import com.example.gitdroid.domain.NetworkRepository
import com.example.gitdroid.models.domain.GHRepository

class NetworkRepositoryImpl(private val networkService: NetworkService): NetworkRepository {
    override suspend fun getReposByUser(name: String): List<GHRepository> {
        Log.d(TAG, "getReposByUser() called with: name = $name")
        val resultList = networkService.getReposByUser(name)
        return resultList
    }

    companion object {
        const val TAG = "NetworkRepoLog"
    }
}