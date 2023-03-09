package com.example.gitdroid.domain

import com.example.gitdroid.models.domain.SearchResult

interface GithubInteractor {
    suspend fun getCodeSearch(searchQuery: String): SearchResult
}