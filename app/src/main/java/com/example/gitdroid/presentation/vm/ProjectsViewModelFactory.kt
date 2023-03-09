package com.example.gitdroid.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gitdroid.domain.ProjectsInteractor
import javax.inject.Inject

class ProjectsViewModelFactory @Inject constructor(private val projectsInteractor: ProjectsInteractor) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ProjectsInteractor::class.java)
            .newInstance(projectsInteractor)
    }
}
