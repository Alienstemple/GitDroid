package com.example.gitdroid.di

import android.content.Context
import com.example.gitdroid.data.search.network.GithubApiService
import com.example.gitdroid.data.search.network.NetworkRepositoryImpl
import com.example.gitdroid.data.search.network.NetworkService
import com.example.gitdroid.data.auth.SessionManager
import com.example.gitdroid.domain.search.GithubInteractor
import com.example.gitdroid.domain.search.GithubInteractorImpl
import com.example.gitdroid.domain.search.NetworkRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SearchResultModule {

    @Provides
    @Singleton
    fun providesGithubInteractor(
        networkRepository: NetworkRepository
    ): GithubInteractor {
        return GithubInteractorImpl(networkRepository)
    }

    @Provides
    @Singleton
    fun providesNetworkRepository(
        networkService: NetworkService
    ): NetworkRepository {
        return NetworkRepositoryImpl(networkService)
    }

    @Provides
    @Singleton
    fun providesNetworkService(
        sessionManager: SessionManager
    ): NetworkService {

        return NetworkService(GithubApiService.getInstance(), sessionManager)
    }

    @Provides
    @Singleton
    fun providesSessionManager(
        context: Context
    ): SessionManager {
        return SessionManager(context)
    }
}