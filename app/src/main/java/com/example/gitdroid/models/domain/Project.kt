package com.example.gitdroid.models.domain

data class Project(
    var id: String = "",
    var name: String = "",
//    val searchResList: List<String> = emptyList()  // TODO search results
    var searchResList: List<SearchResultItem> = emptyList()
)
