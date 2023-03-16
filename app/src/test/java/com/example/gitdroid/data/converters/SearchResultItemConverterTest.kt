package com.example.gitdroid.data.converters

import com.example.gitdroid.MiscCreator
import com.example.gitdroid.models.data.SearchResultItemData
import com.example.gitdroid.models.domain.SearchResult
import com.example.gitdroid.models.domain.SearchResultItem
import com.google.common.truth.Truth.assertThat

internal class SearchResultItemConverterTest {

    private val searchResultItemConverter = SearchResultItemConverter()
    private val stubSearchResultItemData: SearchResultItemData =
        MiscCreator.createSearchResultItemData()
    private val stubSearchResultItem: SearchResultItem = MiscCreator.createSearchResultItem()

    @org.junit.Test
    fun convertFrom() {

        val result = searchResultItemConverter.convert(stubSearchResultItemData)

        assertThat(result).isEqualTo(stubSearchResultItem)
    }

    @org.junit.Test
    fun convertTo() {

        val result = searchResultItemConverter.convert(stubSearchResultItem)

        assertThat(result).isEqualTo(stubSearchResultItemData)
    }
}