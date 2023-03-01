package com.example.gitdroid.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gitdroid.domain.GithubInteractor
import com.example.gitdroid.domain.ProjectsInteractor

class ProjectsViewModelFactory(val projectsInteractor: ProjectsInteractor) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ProjectsInteractor::class.java)
            .newInstance(projectsInteractor)
    }
}
