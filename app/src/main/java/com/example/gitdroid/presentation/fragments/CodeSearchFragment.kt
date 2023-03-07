package com.example.gitdroid.presentation.fragments

import android.app.Dialog
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
import androidx.recyclerview.widget.RecyclerView
import com.example.gitdroid.R
import com.example.gitdroid.data.GithubApiService
import com.example.gitdroid.data.NetworkRepositoryImpl
import com.example.gitdroid.data.NetworkService
import com.example.gitdroid.data.SessionManager
import com.example.gitdroid.databinding.FragmentCodeSearchBinding
import com.example.gitdroid.databinding.ProjectsDialogBoxBinding
import com.example.gitdroid.domain.GithubInteractorImpl
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResultItem
import com.example.gitdroid.presentation.MainActivity
import com.example.gitdroid.presentation.adapters.ProjectsAdapter
import com.example.gitdroid.presentation.adapters.SearchResultAdapter
import com.example.gitdroid.presentation.misc.ProjectItemClickListener
import com.example.gitdroid.presentation.misc.SearchResultItemClickListener
import com.example.gitdroid.presentation.misc.navigation
import com.example.gitdroid.presentation.vm.SearchResultViewModel
import com.example.gitdroid.presentation.vm.GithubViewModelFactory
import com.example.gitdroid.presentation.vm.ProjectsViewModel

class CodeSearchFragment : Fragment(), SearchResultItemClickListener, ProjectItemClickListener {

    private lateinit var binding: FragmentCodeSearchBinding
    private lateinit var dialogBinding: ProjectsDialogBoxBinding

    private lateinit var searchResultViewModel: SearchResultViewModel
    private lateinit var searchResultAdapter: SearchResultAdapter

    private lateinit var networkService: NetworkService
    private lateinit var networkRepository: NetworkRepositoryImpl
    private lateinit var githubInteractor: GithubInteractorImpl

    private lateinit var projectSharedViewModel: ProjectsViewModel

    private lateinit var selectedSearchResult: SearchResultItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCodeSearchBinding.inflate(layoutInflater)
        dialogBinding = ProjectsDialogBoxBinding.inflate(layoutInflater)
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
        Log.d(ProjectsFragment.TAG,
            "onAddToProjectClicked() called with: searchResultItem = $searchResultItem")

        selectedSearchResult = searchResultItem  // Init selected search result

        projectSharedViewModel.projectList

        val dialogBox = Dialog(requireContext())
        dialogBox.setContentView(dialogBinding.root)

        val projectsAdapter = ProjectsAdapter(this as ProjectItemClickListener)
        dialogBinding.dialogRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        dialogBinding.dialogRecyclerView.adapter = projectsAdapter

        projectSharedViewModel.projectList.observe(viewLifecycleOwner) { projectItemsList ->
            projectItemsList?.let {
                // Обновляем Recycler View
                projectsAdapter.setList(it)
            }
        }

        dialogBox.show()
    }

    override fun onItemClicked(project: Project) {
        Log.d(TAG, "onItemClicked() called with: project = $project")
        projectSharedViewModel.updateProject(project.id, selectedSearchResult)
    }

    companion object {
        const val TAG = "CodeSearchFragLog"

        @JvmStatic
        fun newInstance() =
            CodeSearchFragment()
    }
}