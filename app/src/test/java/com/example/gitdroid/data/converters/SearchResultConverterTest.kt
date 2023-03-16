package com.example.gitdroid.data.converters

import com.example.gitdroid.MiscCreator
import com.example.gitdroid.models.data.SearchResultData
import com.example.gitdroid.models.domain.SearchResult
import com.google.common.truth.Truth

internal class SearchResultConverterTest {

    private val searchResultConverter = SearchResultConverter()
    private val stubSearchResultData: SearchResultData = MiscCreator.createSearchResultData()
    private val stubSearchResult: SearchResult = MiscCreator.createSearchResult()

    @org.junit.Test
    fun convertFrom() {
        val result = searchResultConverter.convert(stubSearchResultData)

        Truth.assertThat(result).isEqualTo(stubSearchResult)
    }
}