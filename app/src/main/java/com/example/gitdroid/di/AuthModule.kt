package com.example.gitdroid.di

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.example.gitdroid.data.ProjectsFirebaseRepositoryImpl
import com.example.gitdroid.data.room.ProjectDatabase
import com.example.gitdroid.data.room.ProjectsRoomRepository
import com.example.gitdroid.data.room.ProjectsRoomRepositoryImpl
import com.example.gitdroid.domain.*
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
        context: Context,
        fragmentActivity: FragmentActivity
    ): AuthRepository {
        return AuthRepositoryImpl(context, fragmentActivity)
    }
}