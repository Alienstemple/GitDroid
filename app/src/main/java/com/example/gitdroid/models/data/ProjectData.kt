package com.example.gitdroid.models.data

data class ProjectData(
    var id: String = "",
    var name: String = "",
    var searchResList: List<SearchResultItemData> = emptyList()
)
