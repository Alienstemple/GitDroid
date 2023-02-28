package com.example.gitdroid.domain

import com.example.gitdroid.models.domain.GHRepository
import com.example.gitdroid.models.domain.Issue
import com.example.gitdroid.models.domain.SearchResult
import retrofit2.http.Header

interface NetworkRepository {
    suspend fun getCodeSearch(searchQuery: String): SearchResult
    suspend fun getReposByUser(name: String): List<GHRepository>
    suspend fun getIssuesByUserAndRepository(name: String, repo: String): List<Issue>
}