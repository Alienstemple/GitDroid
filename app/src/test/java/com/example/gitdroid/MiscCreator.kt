package com.example.gitdroid

import com.example.gitdroid.models.data.SearchResultData
import com.example.gitdroid.models.domain.*

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

    fun createProjectList(): List<Project> =
        emptyList()

    fun createSearchResultItem(): SearchResultItem =
        SearchResultItem(
            fileName = "",
            filePath = "",
            htmlFileUrl = "",
            ghRepository = createGhRepository(),
            searchResScore = 0F
        )

    fun createGhRepository() =
        GHRepository(
            repoId = 0,
            repoName = "",
            repoFullName = "",
            repoOwner = createGhUser()
        )
    fun createGhUser() =
        User(
            ghLogin = "",
            userId = 0
        )
}