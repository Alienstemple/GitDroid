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
import com.example.gitdroid.data.*
import com.example.gitdroid.databinding.FragmentProjectsBinding
import com.example.gitdroid.domain.ProjectsFirebaseRepository
import com.example.gitdroid.domain.ProjectsInteractor
import com.example.gitdroid.domain.ProjectsInteractorImpl
import com.example.gitdroid.models.domain.GHRepository
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResultItem
import com.example.gitdroid.presentation.MainActivity
import com.example.gitdroid.presentation.adapters.ProjectsAdapter
import com.example.gitdroid.presentation.misc.ProjectItemClickListener
import com.example.gitdroid.presentation.misc.RepositoryItemClickListener
import com.example.gitdroid.presentation.misc.SearchResultItemClickListener
import com.example.gitdroid.presentation.vm.ProjectsViewModel
import com.example.gitdroid.presentation.vm.ProjectsViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProjectsFragment : Fragment(), ProjectItemClickListener {

    private lateinit var binding: FragmentProjectsBinding

    private lateinit var projectsViewModel: ProjectsViewModel
    private lateinit var projectsAdapter: ProjectsAdapter

    private lateinit var projectsFirebaseRepository: ProjectsFirebaseRepository
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

        projectsFirebaseRepository = ProjectsFirebaseRepositoryImpl()
        projectsInteractor = ProjectsInteractorImpl(projectsFirebaseRepository)
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

            CoroutineScope(Dispatchers.IO).launch {
                // TODO only for test
                Log.d(TAG, "In coro scope, before updating project")
                projectsViewModel.updateProject("howdy", SearchResultItem("name", "", "url", GHRepository(), 0.0F))

//                Log.d(TAG, "In coro scope, before adding project")
//                projectsViewModel.addProject(binding.enterNewProjNameEditText.text.toString())
            }

        }
    }

    private fun setupObserver(projectsViewModel: ProjectsViewModel) {
        projectsViewModel.projectList.observe(viewLifecycleOwner) { projectItemsList ->
            projectItemsList?.let {
                // Обновляем Recycler View
                projectsAdapter.setList(it)
            }
        }
    }

    private fun initAdapter() {
        projectsAdapter = ProjectsAdapter(this as ProjectItemClickListener)
        binding.projectsRecycler.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.projectsRecycler.adapter = projectsAdapter
    }

    override fun onItemClicked(project: Project) {
        Log.d(MainActivity.TAG, "On item clicked: ${project.name}")
        // TODO открыть выпадающий список
    }

    companion object {
        const val TAG = "ProjFragLog"

        @JvmStatic
        fun newInstance() =
            ProjectsFragment()
    }
}