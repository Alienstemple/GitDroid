package com.example.gitdroid.presentation.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitdroid.domain.GithubInteractor
import com.example.gitdroid.models.domain.GHRepository
import kotlinx.coroutines.launch

class RepositoryViewModel (private val githubInteractor: GithubInteractor) : ViewModel() {

    private val _repoList = MutableLiveData<List<GHRepository>>()
    val repoList: LiveData<List<GHRepository>> = _repoList

    fun getReposByUser(userName: String) {
        Log.d(TAG, "getReposByUser() called with: userName = $userName")

        viewModelScope.launch {

            val resultList = githubInteractor.getReposByUser(userName)
            _repoList.postValue(resultList)
        }
    }

    companion object {
        const val TAG = "RepoVMLog"
    }
}