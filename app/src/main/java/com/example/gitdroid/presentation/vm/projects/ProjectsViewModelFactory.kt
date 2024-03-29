package com.example.gitdroid.presentation.vm.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gitdroid.domain.projects.ProjectsInteractor
import com.example.gitdroid.presentation.vm.search.SearchResultViewModel
import javax.inject.Inject

/**
 * Фабрика для создания [ProjectsViewModel]
 */
class ProjectsViewModelFactory @Inject constructor(private val projectsInteractor: ProjectsInteractor) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ProjectsInteractor::class.java)
            .newInstance(projectsInteractor)
    }
}
