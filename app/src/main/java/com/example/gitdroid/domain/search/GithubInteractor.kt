package com.example.gitdroid.domain.search

import com.example.gitdroid.models.domain.SearchResult

interface GithubInteractor {

    /**
     * @throws
     */
    suspend fun getCodeSearch(searchQuery: String): SearchResult
}