package com.example.gitdroid.models.domain

data class Project(
    val name: String,
    val searchResList: List<String>  // TODO search results
//    val searchResList: List<SearchResultItem>
)
