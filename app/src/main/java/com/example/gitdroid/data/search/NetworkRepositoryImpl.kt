package com.example.gitdroid.data.search

import com.example.gitdroid.data.converters.SearchResultConverter
import com.example.gitdroid.domain.search.NetworkRepository
import com.example.gitdroid.models.domain.SearchResult

/**
 * Имплементирует интерфейс [NetworkRepository]
 * Отвечает за получение результата поиска по коду от [NetworkService]
 * @constructor Принимает экземпляры [NetworkService], [SearchResultConverter]
 */
class NetworkRepositoryImpl(
    private val networkService: NetworkService,
    private val searchResultConverter: SearchResultConverter,
) : NetworkRepository {
    override suspend fun getCodeSearch(searchQuery: String): SearchResult {
        val result = networkService.getCodeSearch(searchQuery)
        return searchResultConverter.convert(result)
    }

    companion object {
        const val TAG = "NetworkRepoLog"
    }
}