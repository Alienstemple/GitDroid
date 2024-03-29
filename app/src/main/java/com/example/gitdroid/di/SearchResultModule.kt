package com.example.gitdroid.di

import com.example.gitdroid.data.auth.SessionManager
import com.example.gitdroid.data.converters.SearchResultConverter
import com.example.gitdroid.data.search.GithubApiService
import com.example.gitdroid.data.search.NetworkRepositoryImpl
import com.example.gitdroid.data.search.NetworkService
import com.example.gitdroid.domain.search.GithubInteractor
import com.example.gitdroid.domain.search.GithubInteractorImpl
import com.example.gitdroid.domain.search.NetworkRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class SearchResultModule {

    @Provides
    @Singleton
    fun providesGithubInteractor(
        networkRepository: NetworkRepository,
    ): GithubInteractor {
        return GithubInteractorImpl(networkRepository)
    }

    @Provides
    @Singleton
    fun providesNetworkRepository(
        networkService: NetworkService,
    ): NetworkRepository {
        return NetworkRepositoryImpl(networkService, SearchResultConverter())
    }

    @Provides
    @Singleton
    fun providesNetworkService(
        sessionManager: SessionManager,
    ): NetworkService {


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
        val githubApiService = retrofit.create(GithubApiService::class.java)

        return NetworkService(githubApiService, sessionManager)
    }
}