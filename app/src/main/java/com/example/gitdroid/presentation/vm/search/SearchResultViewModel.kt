package com.example.gitdroid.presentation.vm.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitdroid.domain.search.GithubInteractor
import com.example.gitdroid.models.domain.SearchResultItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchResultViewModel (private val githubInteractor: GithubInteractor) : ViewModel() {

    private val _searchResultItems = MutableLiveData<List<SearchResultItem>>()
    val searchResultItems: LiveData<List<SearchResultItem>> = _searchResultItems

    fun getCodeSearch(searchQuery: String) {
        Log.d(TAG, "getCodeSearch() called with: searchQuery = $searchQuery")

        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, Thread.currentThread().toString())
            val searchResult = githubInteractor.getCodeSearch(searchQuery)
            _searchResultItems.postValue(searchResult.items)
        }
    }

    companion object {
        const val TAG = "SearchResVMLog"
    }
}