package com.example.gitdroid.data.search

import com.example.gitdroid.models.data.SearchResultData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface GithubApiService {

    @GET("search/code")
    suspend fun getCodeSearch(
        @Header("Authorization") token: String,
        @Query("q") searchQuery: String,
    ): Response<SearchResultData>
}
