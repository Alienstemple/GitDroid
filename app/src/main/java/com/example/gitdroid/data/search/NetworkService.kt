package com.example.gitdroid.data.search

import android.util.Log
import com.example.gitdroid.data.auth.SessionManager
import com.example.gitdroid.models.data.SearchResultData

/**
 * Отвечает за получение результата поиска и обработку ошибок.
 * @constructor Принимает экземпляры [GithubApiService], [SessionManager]
 */
class NetworkService(
    private val githubApiService: GithubApiService,
    private val sessionManager: SessionManager,
) {

    /**
     * Получение результата поиска и обработка ошибок. Проверяет, не пустое ли тело ответа
     * @param searchQuery поисковый запрос
     * @return [SearchResultData]
     * @throws RuntimeException
     */
    suspend fun getCodeSearch(searchQuery: String): SearchResultData {
        val token = "Bearer ${sessionManager.fetchAuthToken().toString()}"
        Log.d(TAG, "Token fetched: $token")
        val result = githubApiService.getCodeSearch(token, searchQuery)

        if (result.isSuccessful) {
            result.body()?.let {
                return it
            }
            throw RuntimeException("Blank search body")
        } else {
            throw RuntimeException("Error from network: ${result.code()}")
        }
    }

    companion object {
        const val TAG = "NetwServiceLog"
    }
}