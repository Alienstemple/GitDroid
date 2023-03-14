package com.example.gitdroid.di

import android.content.Context
import com.example.gitdroid.data.converters.ProjectConverter
import com.example.gitdroid.data.converters.SearchResultItemConverter
import com.example.gitdroid.data.projects.ProjectsFirebaseRepositoryImpl
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
    ): ProjectsInteractor {
        return ProjectsInteractorImpl(projectsFirebaseRepository)
    }

    @Provides
    @Singleton
    fun providesProjectsFirebaseRepository(): ProjectsFirebaseRepository {
        return ProjectsFirebaseRepositoryImpl(ProjectConverter(), SearchResultItemConverter())
    }
}