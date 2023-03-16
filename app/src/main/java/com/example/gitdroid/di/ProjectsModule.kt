package com.example.gitdroid.di

import com.example.gitdroid.data.converters.ProjectConverter
import com.example.gitdroid.data.converters.SearchResultItemConverter
import com.example.gitdroid.data.projects.ProjectsRepositoryImpl
import com.example.gitdroid.domain.projects.ProjectsInteractor
import com.example.gitdroid.domain.projects.ProjectsInteractorImpl
import com.example.gitdroid.domain.projects.ProjectsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ProjectsModule {

    @Provides
    @Singleton
    fun providesProjectInteractor(
        projectsRepository: ProjectsRepository,
    ): ProjectsInteractor {
        return ProjectsInteractorImpl(projectsRepository)
    }

    @Provides
    @Singleton
    fun providesProjectsRepository(auth: FirebaseAuth): ProjectsRepository {
        val databaseReference =
            FirebaseDatabase.getInstance().getReference("users").child(auth.currentUser!!.uid)
        return ProjectsRepositoryImpl(ProjectConverter(),
            SearchResultItemConverter(),
            databaseReference)
    }
}
