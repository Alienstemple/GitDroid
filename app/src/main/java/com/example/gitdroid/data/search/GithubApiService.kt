package com.example.gitdroid.data.search

import com.example.gitdroid.models.data.SearchResultData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Интерфейс, использующий GitHub API для поиска по коду на GitHub.
 * Реализован с помощью библиотеки Retrofit
 */
interface GithubApiService {
    /**
     * Get-запрос - поиск совпадений текста запроса [q] глобально по всему GitHub
     * @param q Запрос
     * @param token OAuth2-токен
     * @return Информации о компании (TickerData)
     */
    @GET("search/code")
    suspend fun getCodeSearch(
        @Header("Authorization") token: String,
        @Query("q") searchQuery: String,
    ): Response<SearchResultData>
}
