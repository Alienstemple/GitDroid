package com.example.gitdroid.data.converters

import com.example.gitdroid.models.data.SearchResultData
import com.example.gitdroid.models.domain.SearchResult

class SearchResultConverter {

    private val converter = SearchResultItemConverter()

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