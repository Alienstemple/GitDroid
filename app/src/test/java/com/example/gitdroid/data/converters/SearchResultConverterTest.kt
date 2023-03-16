package com.example.gitdroid.data.converters

import com.example.gitdroid.MiscCreator
import com.example.gitdroid.models.data.ProjectData
import com.example.gitdroid.models.data.SearchResultData
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResult
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

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