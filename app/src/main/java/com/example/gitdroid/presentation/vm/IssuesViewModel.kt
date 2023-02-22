package com.example.gitdroid.presentation.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitdroid.domain.GithubInteractor
import com.example.gitdroid.models.domain.Issue
import kotlinx.coroutines.launch

class IssuesViewModel (private val githubInteractor: GithubInteractor) : ViewModel() {

        private val _issueList = MutableLiveData<List<Issue>>()
        val issueList: LiveData<List<Issue>> = _issueList

        fun getIssueByUserAndRepository(userName: String, repo: String) {
            Log.d(TAG,
                "getIssueByUserAndRepository() called with: userName = $userName, repo = $repo")

            viewModelScope.launch {
                val resultList = githubInteractor.getIssuesByUserAndRepository(userName, repo)
                _issueList.postValue(resultList)
            }
        }

        companion object {
            const val TAG = "IssueVMLog"
        }
    }