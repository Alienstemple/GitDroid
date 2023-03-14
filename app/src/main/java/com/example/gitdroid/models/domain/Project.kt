package com.example.gitdroid.models.domain

data class Project(
    var projectId: String,
    var projectName: String,
    var searchResList: List<SearchResultItem> = emptyList()
)
