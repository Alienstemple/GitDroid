package com.example.gitdroid.data.search

import android.util.Log
import com.example.gitdroid.data.converters.SearchResultConverter
import com.example.gitdroid.domain.search.NetworkRepository
import com.example.gitdroid.models.data.SearchResultData
import com.example.gitdroid.models.domain.SearchResult

class NetworkRepositoryImpl(private val networkService: NetworkService,
                            private val searchResultConverter: SearchResultConverter): NetworkRepository {
    override suspend fun getCodeSearch(searchQuery: String): SearchResult {
        Log.d(TAG, "getCodeSearch() called with: searchQuery = $searchQuery")
        val result = networkService.getCodeSearch(searchQuery)
        return searchResultConverter.convert(result)
    }

    companion object {
        const val TAG = "NetworkRepoLog"
    }
}