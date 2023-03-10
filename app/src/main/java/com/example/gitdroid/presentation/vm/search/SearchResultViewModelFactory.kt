package com.example.gitdroid.presentation.vm.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gitdroid.domain.search.GithubInteractor
import javax.inject.Inject

class SearchResultViewModelFactory @Inject constructor(private val githubInteractor: GithubInteractor):
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(GithubInteractor::class.java).newInstance(githubInteractor)
    }
}
