package com.example.gitdroid.data.search

import android.util.Log
import com.example.gitdroid.domain.search.NetworkRepository
import com.example.gitdroid.models.domain.SearchResult

class NetworkRepositoryImpl(private val networkService: NetworkService): NetworkRepository {
    override suspend fun getCodeSearch(searchQuery: String): SearchResult {
        Log.d(TAG, "getCodeSearch() called with: searchQuery = $searchQuery")

        val searchResult = networkService.getCodeSearch(searchQuery)
        return searchResult
    }

    companion object {
        const val TAG = "NetworkRepoLog"
    }
}