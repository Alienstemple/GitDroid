package com.example.gitdroid.data.projects

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

    fun createSearchResultItemList(): List<SearchResultItem> =
        emptyList()
}