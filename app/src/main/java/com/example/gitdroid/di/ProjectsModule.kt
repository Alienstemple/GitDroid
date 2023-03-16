package com.example.gitdroid.di

import com.example.gitdroid.data.converters.ProjectConverter
import com.example.gitdroid.data.converters.SearchResultItemConverter
import com.example.gitdroid.data.projects.ProjectsFirebaseRepositoryImpl
import com.example.gitdroid.domain.projects.ProjectsFirebaseRepository
import com.example.gitdroid.domain.projects.ProjectsInteractor
import com.example.gitdroid.domain.projects.ProjectsInteractorImpl
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
        projectsFirebaseRepository: ProjectsFirebaseRepository,
    ): ProjectsInteractor {
        return ProjectsInteractorImpl(projectsFirebaseRepository)
    }

    @Provides
    @Singleton
    fun providesProjectsFirebaseRepository(auth: FirebaseAuth): ProjectsFirebaseRepository {
        val databaseReference =
            FirebaseDatabase.getInstance().getReference("users").child(auth.currentUser!!.uid)
        return ProjectsFirebaseRepositoryImpl(ProjectConverter(),
            SearchResultItemConverter(),
            databaseReference)
    }
}
