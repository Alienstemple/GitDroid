package com.example.gitdroid.domain

import com.example.gitdroid.models.domain.SearchResult

interface NetworkRepository {
    suspend fun getCodeSearch(searchQuery: String): SearchResult
}