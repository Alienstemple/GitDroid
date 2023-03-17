package com.example.gitdroid.presentation.vm.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitdroid.domain.search.GithubInteractor
import com.example.gitdroid.models.domain.SearchResultItem
import com.example.gitdroid.models.domain.SearchState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * ViewModel поиска по коду GitHub. Хранит список результатов поиска и текущее состояние
 * поиска в LiveData
 */
class SearchResultViewModel(private val githubInteractor: GithubInteractor) : ViewModel() {

    private val _searchResultItems = MutableLiveData<List<SearchResultItem>>()

    /**
     * Cписок результатов поиска
     */
    val searchResultItems: LiveData<List<SearchResultItem>> = _searchResultItems

    private val _searchState = MutableLiveData<SearchState>()

    /**
     * Текущее состояние поиска [SearchState]
     */
    val searchState: LiveData<SearchState> = _searchState

    /**
     * Поиск по коду GitHub.
     * @param searchQuery Поисковый запрос.
     * Обработка ошибок реализована с помощью [SearchState]
     */
    fun getCodeSearch(searchQuery: String) {

        val handler = CoroutineExceptionHandler { _, exception ->
            Log.d(TAG, "Exception thrown in one of the children. $exception")
            _searchState.postValue(SearchState.ERROR)
        }

        viewModelScope.launch(SupervisorJob() + Dispatchers.IO + handler) {
            _searchState.postValue(SearchState.LOADING)
            val searchResult = githubInteractor.getCodeSearch(searchQuery)
            _searchResultItems.postValue(searchResult.searchResultItems)
            _searchState.postValue(SearchState.COMPLETED)
        }
    }

    companion object {
        const val TAG = "SearchResVMLog"
    }
}