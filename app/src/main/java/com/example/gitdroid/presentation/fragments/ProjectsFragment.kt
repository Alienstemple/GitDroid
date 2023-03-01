package com.example.gitdroid.presentation.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitdroid.data.GithubApiService
import com.example.gitdroid.data.NetworkRepositoryImpl
import com.example.gitdroid.data.NetworkService
import com.example.gitdroid.data.SessionManager
import com.example.gitdroid.databinding.FragmentProjectsBinding
import com.example.gitdroid.domain.GithubInteractorImpl
import com.example.gitdroid.domain.ProjectsInteractor
import com.example.gitdroid.domain.ProjectsInteractorImpl
import com.example.gitdroid.presentation.adapters.ProjectsAdapter
import com.example.gitdroid.presentation.adapters.SearchResultAdapter
import com.example.gitdroid.presentation.misc.ProjectItemClickListener
import com.example.gitdroid.presentation.misc.SearchResultItemClickListener
import com.example.gitdroid.presentation.vm.ProjectsViewModel
import com.example.gitdroid.presentation.vm.SearchResultViewModel
import com.example.gitdroid.presentation.vm.GithubViewModelFactory
import com.example.gitdroid.presentation.vm.ProjectsViewModelFactory

class ProjectsFragment : Fragment() {

    private lateinit var binding: FragmentProjectsBinding

    private lateinit var projectsViewModel: ProjectsViewModel
    private lateinit var projectsAdapter: ProjectsAdapter

    // TODO DbService, DbRepo, ProjectsInteractor
    private lateinit var projectsInteractor: ProjectsInteractor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProjectsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // TODO service, repository
        projectsInteractor = ProjectsInteractorImpl()
        projectsViewModel =
            ViewModelProvider(this,
                ProjectsViewModelFactory(projectsInteractor))[ProjectsViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Init adapter for recycler
        initAdapter()
        // Init observer
        setupObserver(projectsViewModel)

        binding.addBtn.setOnClickListener {
            // TODO move to VM
//            projectsViewModel.addNewProject(binding.enterNewProjNameEditText.text.toString())
        }
    }

    private fun setupObserver(projectsViewModel: ProjectsViewModel) {
//        projectsViewModel.projectItems.observe(viewLifecycleOwner) { projectItemsList ->
//            projectItemsList?.let {
//                // Обновляем Recycler View
//                projectsAdapter.setList(it)
//            }
//        }
    }

    private fun initAdapter() {
//        projectsAdapter = projectsAdapter(activity as ProjectItemClickListener)
//        binding.projectsRecycler.layoutManager =
//            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
//        binding.projectsRecycler.adapter = projectsAdapter
    }

    companion object {
        const val TAG = "ProjFragLog"

        @JvmStatic
        fun newInstance() =
            ProjectsFragment()
    }
}