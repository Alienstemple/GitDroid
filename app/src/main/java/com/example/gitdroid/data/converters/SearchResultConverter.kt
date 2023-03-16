package com.example.gitdroid.data.converters

import com.example.gitdroid.models.data.SearchResultData
import com.example.gitdroid.models.domain.SearchResult

/**
 * Служебный класс для преобразования модели [SearchResultData] уровня data
 * в модель [SearchResult] уровня domain
 */
class SearchResultConverter {

    private val converter = SearchResultItemConverter()

    /**
     * Преобразоване модели [SearchResultData] уровня data
     * в модель [SearchResult] уровня domain
     * @param searchResultData
     * @return [SearchResult]
     */
    fun convert(searchResultData: SearchResultData): SearchResult {
        return SearchResult(
            totalResultCount = searchResultData.totalCount,
            incompleteResults = searchResultData.incompleteResults,
            searchResultItems = searchResultData.items.map {
                converter.convert(it)
            }
        )
    }
}