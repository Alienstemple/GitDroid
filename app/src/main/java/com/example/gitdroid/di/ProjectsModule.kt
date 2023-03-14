package com.example.gitdroid.di

import android.content.Context
import com.example.gitdroid.data.converters.ProjectConverter
import com.example.gitdroid.data.converters.SearchResultItemConverter
import com.example.gitdroid.data.projects.ProjectsFirebaseRepositoryImpl
import com.example.gitdroid.data.search.room.ProjectDatabase
import com.example.gitdroid.data.search.room.ProjectsRoomRepository
import com.example.gitdroid.data.search.room.ProjectsRoomRepositoryImpl
import com.example.gitdroid.domain.projects.ProjectsFirebaseRepository
import com.example.gitdroid.domain.projects.ProjectsInteractor
import com.example.gitdroid.domain.projects.ProjectsInteractorImpl
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
        return ProjectsFirebaseRepositoryImpl(ProjectConverter(), SearchResultItemConverter())
    }

    @Provides
    @Singleton
    fun providesProjectsRoomRepository(context: Context): ProjectsRoomRepository {
        val dao = ProjectDatabase.getDatabaseClient(context).projectDao()
        return ProjectsRoomRepositoryImpl(dao)
    }
}