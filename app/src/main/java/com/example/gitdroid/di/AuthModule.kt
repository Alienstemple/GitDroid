package com.example.gitdroid.di

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.example.gitdroid.domain.auth.AuthInteractor
import com.example.gitdroid.domain.auth.AuthInteractorImpl
import com.example.gitdroid.domain.auth.AuthRepository
import com.example.gitdroid.domain.auth.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AuthModule {
    @Provides
    @Singleton
    fun providesAuthInteractor(
        authRepository: AuthRepository
    ): AuthInteractor {
        return AuthInteractorImpl(authRepository)
    }

    @Provides
    @Singleton
    fun providesAuthRepository(
        context: Context
    ): AuthRepository {
        return AuthRepositoryImpl(context)
    }
}