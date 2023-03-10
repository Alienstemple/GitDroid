package com.example.gitdroid.domain.search

import com.example.gitdroid.models.domain.SearchResult

interface NetworkRepository {
    suspend fun getCodeSearch(searchQuery: String): SearchResult
}