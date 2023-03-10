package com.example.gitdroid.di

import android.content.Context
import com.example.gitdroid.data.*
import com.example.gitdroid.data.room.ProjectDatabase
import com.example.gitdroid.data.room.ProjectsRoomRepository
import com.example.gitdroid.data.room.ProjectsRoomRepositoryImpl
import com.example.gitdroid.domain.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ProjectsModule {

    @Provides
    @Singleton
    fun providesProjectInteractor(
        projectsFirebaseRepository: ProjectsFirebaseRepository,
        projectsRoomRepository: ProjectsRoomRepository,
    ): ProjectsInteractor {
        return ProjectsInteractorImpl(projectsFirebaseRepository, projectsRoomRepository)
    }

    @Provides
    @Singleton
    fun providesProjectsFirebaseRepository(): ProjectsFirebaseRepository {
        return ProjectsFirebaseRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providesProjectsRoomRepository(context: Context): ProjectsRoomRepository {
        val dao = ProjectDatabase.getDatabaseClient(context).projectDao()
        return ProjectsRoomRepositoryImpl(dao)
    }
}