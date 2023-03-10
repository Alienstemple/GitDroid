package com.example.gitdroid.di

import android.content.Context
import com.example.gitdroid.presentation.MainActivity
import com.example.gitdroid.presentation.fragments.CodeSearchFragment
import com.example.gitdroid.presentation.fragments.ProjectsFragment
import com.example.gitdroid.presentation.vm.ProjectsViewModelFactory
import com.example.gitdroid.presentation.vm.SearchResultViewModelFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ProjectsModule::class, SearchResultModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun projectsViewModelFactory(): ProjectsViewModelFactory
    fun searchResultViewModelFactory(): SearchResultViewModelFactory
    fun inject(activity: MainActivity)
    fun inject(codeSearchFragment: CodeSearchFragment)
    fun inject(projectsFragment: ProjectsFragment)
}