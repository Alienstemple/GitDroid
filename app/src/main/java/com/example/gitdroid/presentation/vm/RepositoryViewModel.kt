package com.example.gitdroid.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitdroid.models.domain.GHRepository
import kotlinx.coroutines.launch

class RepositoryViewModel : ViewModel() {

    private val _repoList = MutableLiveData<List<GHRepository>>()
    val repoList: LiveData<List<GHRepository>> = _repoList

    fun getUserRepos(userName: String) {

        viewModelScope.launch {

        }
    }

}