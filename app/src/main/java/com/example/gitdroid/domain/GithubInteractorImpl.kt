package com.example.gitdroid.domain

import android.util.Log
import com.example.gitdroid.models.domain.GHRepository
import com.example.gitdroid.models.domain.Issue
import com.example.gitdroid.models.domain.SearchResult

class GithubInteractorImpl (private val networkRepository: NetworkRepository): GithubInteractor {
    override suspend fun getCodeSearch(searchQuery: String): SearchResult {
        Log.d(TAG, "getCodeSearch() called with: searchQuery = $searchQuery")
        return networkRepository.getCodeSearch(searchQuery)
    }

    override suspend fun getReposByUser(name: String): List<GHRepository> {
        Log.d(TAG, "getReposByUser() called with: name = $name")
        return networkRepository.getReposByUser(name)
    }

    override suspend fun getIssuesByUserAndRepository(name: String, repo: String): List<Issue> {
        Log.d(TAG, "getIssuesByUserAndRepository() called with: name = $name, repo = $repo")
        return networkRepository.getIssuesByUserAndRepository(name, repo)
    }

    companion object {
        const val TAG = "GHInteractLog"
    }
}