package com.example.gitdroid.domain.search

import com.example.gitdroid.models.domain.SearchResult

/**
 * Имплементирует интерфейс для взаимодействия с репозиторием [NetworkRepository]
 * Получение результата поиска по коду GitHub
 * @constructor принимает экземпляр [NetworkRepository]
 */
class GithubInteractorImpl(private val networkRepository: NetworkRepository) : GithubInteractor {
    override suspend fun getCodeSearch(searchQuery: String): SearchResult {
        return networkRepository.getCodeSearch(searchQuery)
    }

    companion object {
        const val TAG = "GHInteractLog"
    }
}