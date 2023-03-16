package com.example.gitdroid.di

import android.content.Context
import com.example.gitdroid.data.auth.AuthRepositoryImpl
import com.example.gitdroid.data.auth.SessionManager
import com.example.gitdroid.domain.auth.AuthInteractor
import com.example.gitdroid.domain.auth.AuthInteractorImpl
import com.example.gitdroid.domain.auth.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AuthModule {
    @Provides
    @Singleton
    fun providesAuthInteractor(
        authRepository: AuthRepository,
    ): AuthInteractor {
        return AuthInteractorImpl(authRepository)
    }

    @Provides
    @Singleton
    fun providesAuthRepository(
        auth: FirebaseAuth,
        sessionManager: SessionManager
    ): AuthRepository {
        val provider = OAuthProvider.newBuilder(PROVIDER_ID)
        return AuthRepositoryImpl(auth, provider, sessionManager)
    }

    @Provides
    @Singleton
    fun providesAuthInstance(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun providesSessionManager(
        context: Context,
    ): SessionManager {
        return SessionManager(context)
    }

    companion object {
        const val PROVIDER_ID = "github.com"
    }
}