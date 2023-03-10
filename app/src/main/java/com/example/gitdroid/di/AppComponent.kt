package com.example.gitdroid.di

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.example.gitdroid.presentation.MainActivity
import com.example.gitdroid.presentation.fragments.CodeSearchFragment
import com.example.gitdroid.presentation.fragments.ProjectsFragment
import com.example.gitdroid.presentation.vm.auth.AuthViewModelFactory
import com.example.gitdroid.presentation.vm.projects.ProjectsViewModelFactory
import com.example.gitdroid.presentation.vm.search.SearchResultViewModelFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ProjectsModule::class, SearchResultModule::class, AuthModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun authViewModelFactory(): AuthViewModelFactory
    fun projectsViewModelFactory(): ProjectsViewModelFactory
    fun searchResultViewModelFactory(): SearchResultViewModelFactory
    fun inject(activity: MainActivity)
    fun inject(codeSearchFragment: CodeSearchFragment)
    fun inject(projectsFragment: ProjectsFragment)
}