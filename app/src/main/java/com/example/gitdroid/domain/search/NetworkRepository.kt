package com.example.gitdroid.domain.search

import com.example.gitdroid.models.domain.SearchResult

/**
 * Интерфейс для поиска по коду GitHub
 */
interface NetworkRepository {
    /**
     * Получение результата поиска
     * @param searchQuery
     * @return [SearchResult]
     */
    suspend fun getCodeSearch(searchQuery: String): SearchResult
}