package com.example.gitdroid.domain.search

import com.example.gitdroid.models.domain.SearchResult

/**
 * Интерфейс для взаимодействия с репозиторием [NetworkRepository]
 * Получение результата поиска по коду GitHub
 */
interface GithubInteractor {

    /**
     * Получение результата поиска
     * @param searchQuery поисковый запрос
     * @return [SearchResult]
     * @throws RuntimeException
    */
    suspend fun getCodeSearch(searchQuery: String): SearchResult
}