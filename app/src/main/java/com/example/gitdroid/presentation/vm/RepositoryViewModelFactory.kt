package com.example.gitdroid.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gitdroid.domain.GithubInteractor

class RepositoryViewModelFactory(val githubInteractor: GithubInteractor):
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(GithubInteractor::class.java).newInstance(githubInteractor)
    }
}
