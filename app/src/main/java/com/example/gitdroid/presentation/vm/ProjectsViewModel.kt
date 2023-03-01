package com.example.gitdroid.presentation.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.gitdroid.domain.GithubInteractor
import com.example.gitdroid.domain.ProjectsInteractor

class ProjectsViewModel(private val projectsInteractor: ProjectsInteractor): ViewModel() {
}