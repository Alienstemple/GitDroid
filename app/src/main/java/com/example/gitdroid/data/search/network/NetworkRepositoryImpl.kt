package com.example.gitdroid.data.search.network

import android.util.Log
import com.example.gitdroid.domain.search.NetworkRepository
import com.example.gitdroid.models.data.SearchResultData

class NetworkRepositoryImpl(private val networkService: NetworkService): NetworkRepository {
    override suspend fun getCodeSearch(searchQuery: String): SearchResultData {
        Log.d(TAG, "getCodeSearch() called with: searchQuery = $searchQuery")

        return networkService.getCodeSearch(searchQuery)
    }

    companion object {
        const val TAG = "NetworkRepoLog"
    }
}