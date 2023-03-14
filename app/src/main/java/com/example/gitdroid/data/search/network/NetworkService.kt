package com.example.gitdroid.data.search.network

import android.util.Log
import com.example.gitdroid.data.auth.SessionManager
import com.example.gitdroid.models.data.SearchResultData

class NetworkService(private val githubApiService: GithubApiService, private val sessionManager: SessionManager) {

    suspend fun getCodeSearch(searchQuery: String): SearchResultData {
        Log.d(TAG, "NetworkService called with: searchQuery = $searchQuery")
        val token = "Bearer ${sessionManager.fetchAuthToken().toString()}"
        Log.d(TAG, "Token fetched: $token")
        val result = githubApiService.getCodeSearch(token, searchQuery)
        Log.d(TAG, "From network we have: ${result.code()} ${result.isSuccessful} ${
            result.body().toString()
        }")
        result.body()?.let {
            Log.d(TAG, "From network we have: $it")
            return it
        }
        throw RuntimeException("Code search returned null") // TODO обработать runtime exception
    }

    companion object {
        const val TAG = "NetwServiceLog"
    }
}