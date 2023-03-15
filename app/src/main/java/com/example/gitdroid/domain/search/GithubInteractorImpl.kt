package com.example.gitdroid.domain.search

import android.util.Log
import com.example.gitdroid.models.domain.SearchResult

class GithubInteractorImpl (private val networkRepository: NetworkRepository): GithubInteractor {
    override suspend fun getCodeSearch(searchQuery: String): SearchResult {
        Log.d(TAG, "getCodeSearch() called with: searchQuery = $searchQuery")
        return networkRepository.getCodeSearch(searchQuery)
    }

    companion object {
        const val TAG = "GHInteractLog"
    }
}