package com.example.gitdroid.di

import android.content.Context
import com.example.gitdroid.presentation.MainActivity
import com.example.gitdroid.presentation.fragments.CodeSearchFragment
import com.example.gitdroid.presentation.fragments.ProjectsFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [GeneralModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(codeSearchFragment: CodeSearchFragment)
    fun inject(projectsFragment: ProjectsFragment)
}