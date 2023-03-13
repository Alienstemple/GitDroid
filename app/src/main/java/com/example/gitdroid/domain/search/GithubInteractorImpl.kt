package com.example.gitdroid.domain.search

import android.util.Log
import com.example.gitdroid.data.converters.SearchResultConverter
import com.example.gitdroid.models.domain.SearchResult

class GithubInteractorImpl (private val networkRepository: NetworkRepository): GithubInteractor {
    override suspend fun getCodeSearch(searchQuery: String): SearchResult {
        Log.d(TAG, "getCodeSearch() called with: searchQuery = $searchQuery")
        val result = networkRepository.getCodeSearch(searchQuery)
        return SearchResultConverter().convert(result)
    }

    companion object {
        const val TAG = "GHInteractLog"
    }
}