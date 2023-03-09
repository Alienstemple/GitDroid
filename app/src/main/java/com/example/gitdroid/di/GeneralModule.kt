package com.example.gitdroid.di

import android.content.Context
import com.example.gitdroid.data.ProjectsFirebaseRepositoryImpl
import com.example.gitdroid.data.room.ProjectDatabase
import com.example.gitdroid.data.room.ProjectsRoomRepository
import com.example.gitdroid.domain.ProjectsFirebaseRepository
import com.example.gitdroid.domain.ProjectsInteractor
import com.example.gitdroid.domain.ProjectsInteractorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GeneralModule {
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
        return ProjectsRoomRepository(dao)  // TODO Repo impl
    }
}