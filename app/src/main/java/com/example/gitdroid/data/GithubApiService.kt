package com.example.gitdroid.data

import com.example.gitdroid.models.domain.GHRepository
import com.example.gitdroid.models.domain.SearchResult
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query


interface GithubApiService {

    @GET("search/code")
    suspend fun getCodeSearch(@Header("Authorization") token: String, @Query("q") searchQuery: String): Response<SearchResult>

    companion object {
        var retrofitService: GithubApiService? = null
        fun getInstance(): GithubApiService {
            if (retrofitService == null) {

                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                val okHttp = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttp)
                    .build()
                retrofitService = retrofit.create(GithubApiService::class.java)
            }
            return retrofitService!!
        }

    }
}
