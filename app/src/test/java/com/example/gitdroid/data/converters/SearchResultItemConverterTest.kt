package com.example.gitdroid.data.converters

import com.example.gitdroid.MiscCreator
import com.example.gitdroid.models.data.SearchResultItemData
import com.example.gitdroid.models.domain.SearchResult

internal class SearchResultItemConverterTest {

    private val searchResultItemConverter = SearchResultItemConverter()
    private val stubSearchResultItemData: SearchResultItemData =
        MiscCreator.createSearchResultItemData()
    private val stubSearchResultItem: SearchResult = MiscCreator.createSearchResult()

//    @org.junit.Test
//    fun convertFrom() {
//        every { searchResultItemConverter.convert(stubProjectData) } returns stubProject
//
//        val result = searchResultItemConverter.convert((stubProjectData))
//
//        verify { searchResultItemConverter.convert(stubProjectData) }
//        Truth.assertThat(result).isEqualTo(stubProject)
//    }
//
//    @org.junit.Test
//    fun convertTo() {
//        every { searchResultItemConverter.convert(stubProject) } returns stubProjectData
//
//        val result = searchResultItemConverter.convert((stubProject))
//
//        verify { searchResultItemConverter.convert(stubProject) }
//        Truth.assertThat(result).isEqualTo(stubProjectData)
//    }
}