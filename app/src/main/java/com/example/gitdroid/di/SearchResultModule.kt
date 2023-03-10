package com.example.gitdroid.di

import android.content.Context
import com.example.gitdroid.data.GithubApiService
import com.example.gitdroid.data.NetworkRepositoryImpl
import com.example.gitdroid.data.NetworkService
import com.example.gitdroid.data.SessionManager
import com.example.gitdroid.domain.GithubInteractor
import com.example.gitdroid.domain.GithubInteractorImpl
import com.example.gitdroid.domain.NetworkRepository
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