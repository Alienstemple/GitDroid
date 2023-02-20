package com.example.gitdroid.data

import com.example.gitdroid.models.domain.GHRepository
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApiService {

    @GET("users/{user}/repos")
    suspend fun getReposByUser(@Path("user") user: String): Response<List<GHRepository>>

    companion object {
        var retrofitService: GithubApiService? = null
        fun getInstance(): GithubApiService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(GithubApiService::class.java)
            }
            return retrofitService!!
        }

    }
}
