package com.example.gitdroid.presentation.vm.search

import android.util.Log
import androidx.lifecycle.Observer
import com.example.gitdroid.MiscCreator
import com.example.gitdroid.domain.search.GithubInteractorImpl
import com.example.gitdroid.models.domain.SearchResult
import com.example.gitdroid.models.domain.SearchResultItem
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.*

internal class SearchResultViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val searchResultItemsObserver: Observer<List<SearchResultItem>> = mockk(relaxed = true)
    private val searchStateObserver: Observer<SearchState> = mockk(relaxed = true)

    private lateinit var githubInteractor: GithubInteractorImpl
    private lateinit var searchResultViewModel: SearchResultViewModel
    private val stubSearchResult: SearchResult = MiscCreator.createSearchResult()
    private val stubSearchResultItemList: List<SearchResultItem> = MiscCreator.createSearchResultItemList()
    private val stubQuery = "Search query"

    @Before
    fun setUp() {
        githubInteractor = mockk {
            coEvery {
                getCodeSearch(stubQuery)
            } returns stubSearchResult
        }

        searchResultViewModel = SearchResultViewModel(githubInteractor)

        searchResultViewModel.searchResultItems.observeForever(searchResultItemsObserver)
        searchResultViewModel.searchState.observeForever(searchStateObserver)

        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
    }

    @After
    fun tearDown() {
        searchResultViewModel.searchResultItems.removeObserver(searchResultItemsObserver)
        searchResultViewModel.searchState.removeObserver(searchStateObserver)
        unmockkStatic(Log::class)
    }

    @Test
    fun `Get code search positive result`() {
        // act
        searchResultViewModel.getCodeSearch(stubQuery)

        // assert
        verify { searchStateObserver.onChanged(SearchState.LOADING) }
        coVerify { githubInteractor.getCodeSearch(stubQuery) }
        verify { searchResultItemsObserver.onChanged(stubSearchResultItemList) }
        verify { searchStateObserver.onChanged(SearchState.COMPLETED) }
    }
}