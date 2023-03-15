package com.example.gitdroid.presentation.fragments

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitdroid.GitDroidApplication
import com.example.gitdroid.databinding.FragmentCodeSearchBinding
import com.example.gitdroid.databinding.ProjectsDialogBoxBinding
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResultItem
import com.example.gitdroid.presentation.MainActivity
import com.example.gitdroid.presentation.adapters.ProjectsForSearchAdapter
import com.example.gitdroid.presentation.adapters.SearchResultAdapter
import com.example.gitdroid.presentation.misc.ProjectItemClickListener
import com.example.gitdroid.presentation.misc.SearchResultItemClickListener
import com.example.gitdroid.presentation.vm.projects.ProjectsViewModel
import com.example.gitdroid.presentation.vm.search.SearchResultViewModel
import com.example.gitdroid.presentation.vm.search.SearchState

class CodeSearchFragment : Fragment(), SearchResultItemClickListener, ProjectItemClickListener {

    private lateinit var binding: FragmentCodeSearchBinding
    private lateinit var dialogBinding: ProjectsDialogBoxBinding

    private val searchResultViewModel: SearchResultViewModel by viewModels {
        (activity?.application as GitDroidApplication).appComponent.searchResultViewModelFactory()
    }
    private lateinit var searchResultAdapter: SearchResultAdapter
    private val projectsAdapter = ProjectsForSearchAdapter(this as ProjectItemClickListener)

    private val projectSharedViewModel: ProjectsViewModel by viewModels({ activity as ViewModelStoreOwner },
        { (activity?.application as GitDroidApplication).appComponent.projectsViewModelFactory() })

    private lateinit var selectedSearchResult: SearchResultItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCodeSearchBinding.inflate(layoutInflater)
        dialogBinding = ProjectsDialogBoxBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Init adapter for recycler
        initAdapter()
        // Init observers
        setupSearchResObserver()
        setupProjectsObserver()
        setupUiStateObserver()

        projectSharedViewModel.loadAllProjects()

        binding.goBtn.setOnClickListener {
            searchResultViewModel.getCodeSearch(binding.enterSearchQueryEditText.text.toString())
        }
    }

    private fun setupUiStateObserver() {
        searchResultViewModel.searchState.observe(viewLifecycleOwner) { uiState ->
            when(uiState) {
                SearchState.LOADING -> {
                    Log.d(TAG, "Making progr bar visible")
                    binding.searchResultsRecycler.visibility = View.GONE
                    binding.searchResultsProgress.visibility = View.VISIBLE
                }
                SearchState.ERROR -> {
                    Log.d(TAG, "Before showing alert dialog")
                    binding.searchResultsRecycler.visibility = View.GONE
                    binding.searchResultsProgress.visibility = View.GONE
                    showErrorAlertDialog()
                }
                SearchState.COMPLETED -> {
                    Log.d(TAG, "Making recycler visible")
                    binding.searchResultsRecycler.visibility = View.VISIBLE
                    binding.searchResultsProgress.visibility = View.GONE
                }
                null -> {
                    Log.d(TAG, "Before showing alert dialog")
                    binding.searchResultsRecycler.visibility = View.GONE
                    binding.searchResultsProgress.visibility = View.GONE
                }
            }
        }
    }

    private fun showErrorAlertDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Caution")
        builder.setMessage("Network error")

        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun setupSearchResObserver() {
        searchResultViewModel.searchResultItems.observe(viewLifecycleOwner) { searchResItemsList ->
            searchResItemsList?.let {
                // Обновляем Recycler View
                searchResultAdapter.setList(it)
            }
        }
    }

    private fun setupProjectsObserver() {
        projectSharedViewModel.projectList.observe(viewLifecycleOwner) { projectList ->
            projectList?.let {
                // Обновляем Recycler View
                projectsAdapter.setList(it)
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
        Log.d(MainActivity.TAG, "On item clicked: ${searchResultItem.ghRepository.repoName}")
        val url = searchResultItem.htmlFileUrl
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
    }

    override fun onAddToProjectClicked(searchResultItem: SearchResultItem) {
        Log.d(ProjectsFragment.TAG,
            "onAddToProjectClicked() called with: searchResultItem = $searchResultItem")

        selectedSearchResult = searchResultItem  // Init selected search result

        val dialogBox = Dialog(requireContext())
        dialogBox.setContentView(dialogBinding.root)

        dialogBinding.dialogRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        dialogBinding.dialogRecyclerView.adapter = projectsAdapter

        // Setup observer
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
        projectSharedViewModel.updateProject(project.projectId, selectedSearchResult)
    }

    companion object {
        const val TAG = "CodeSearchFragLog"

        @JvmStatic
        fun newInstance() =
            CodeSearchFragment()
    }
}