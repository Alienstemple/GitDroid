package com.example.gitdroid.presentation.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitdroid.data.GithubApiService
import com.example.gitdroid.data.NetworkRepositoryImpl
import com.example.gitdroid.data.NetworkService
import com.example.gitdroid.data.SessionManager
import com.example.gitdroid.databinding.FragmentCodeSearchBinding
import com.example.gitdroid.domain.GithubInteractorImpl
import com.example.gitdroid.models.domain.SearchResultItem
import com.example.gitdroid.presentation.MainActivity
import com.example.gitdroid.presentation.adapters.SearchResultAdapter
import com.example.gitdroid.presentation.misc.SearchResultItemClickListener
import com.example.gitdroid.presentation.misc.navigation
import com.example.gitdroid.presentation.vm.SearchResultViewModel
import com.example.gitdroid.presentation.vm.GithubViewModelFactory
import com.example.gitdroid.presentation.vm.ProjectsViewModel

class CodeSearchFragment : Fragment(), SearchResultItemClickListener {

    private lateinit var binding: FragmentCodeSearchBinding

    private lateinit var searchResultViewModel: SearchResultViewModel
    private lateinit var searchResultAdapter: SearchResultAdapter

    private lateinit var networkService: NetworkService
    private lateinit var networkRepository: NetworkRepositoryImpl
    private lateinit var githubInteractor: GithubInteractorImpl

    private lateinit var projectSharedViewModel: ProjectsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCodeSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        networkService = NetworkService(GithubApiService.getInstance(), SessionManager(context))
        networkRepository = NetworkRepositoryImpl(networkService)
        githubInteractor = GithubInteractorImpl(networkRepository)
        searchResultViewModel =
            ViewModelProvider(this,
                GithubViewModelFactory(githubInteractor))[SearchResultViewModel::class.java]

        projectSharedViewModel = navigation().getProjectsVm()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Init adapter for recycler
        initAdapter()
        // Init observer
        setupObserver(searchResultViewModel)

        binding.goBtn.setOnClickListener {
            searchResultViewModel.getCodeSearch(binding.enterSearchQueryEditText.text.toString())
        }
    }

    private fun setupObserver(searchResultViewModel: SearchResultViewModel) {
        searchResultViewModel.searchResultItems.observe(viewLifecycleOwner) { searchResItemsList ->
            searchResItemsList?.let {
                // Обновляем Recycler View
                searchResultAdapter.setList(it)
            }
        }
    }

    private fun initAdapter() {
        searchResultAdapter = SearchResultAdapter(this as SearchResultItemClickListener)
        binding.searchResultsRecycler.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.searchResultsRecycler.adapter = searchResultAdapter
    }

    override fun onItemClicked(searchResultItem: SearchResultItem) {
        // Open chrome custom tab
        Log.d(MainActivity.TAG, "On item clicked: ${searchResultItem.repository.name}")
        val url = searchResultItem.html_url
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
    }

    override fun onAddToProjectClicked(searchResultItem: SearchResultItem) {
        Log.d(ProjectsFragment.TAG, "onAddToProjectClicked() called with: searchResultItem = $searchResultItem")

        projectSharedViewModel.testMethod()
        projectSharedViewModel.projectList


    // Get list of projects
        // Alert dialog with recycler
        // Item selected - call projectViewModel -- add
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CodeSearchFragment()
    }
}