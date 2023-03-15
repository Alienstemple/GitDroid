package com.example.gitdroid

import com.example.gitdroid.models.data.SearchResultData
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResult
import com.example.gitdroid.models.domain.SearchResultItem

object MiscCreator {
    fun createProject(): Project =
        Project(
            projectId = "",
            projectName = "",
            searchResList = emptyList()
        )

    fun createSearchResult(): SearchResult =
        SearchResult(
            totalResultCount = 0,
            incompleteResults = false,
            searchResultItems = emptyList()
        )

    fun createSearchResultData(): SearchResultData =
        SearchResultData(
            totalCount = 0,
            incompleteResults = false,
            items = emptyList()
        )

    fun createSearchResultItemList(): List<SearchResultItem> =
        emptyList()
}