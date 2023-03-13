package com.example.gitdroid.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitdroid.GitDroidApplication
import com.example.gitdroid.databinding.FragmentProjectsBinding
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.presentation.MainActivity
import com.example.gitdroid.presentation.adapters.ProjectsAdapter
import com.example.gitdroid.presentation.misc.ProjectItemClickListener
import com.example.gitdroid.presentation.vm.projects.ProjectsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProjectsFragment : Fragment(), ProjectItemClickListener {

    private lateinit var binding: FragmentProjectsBinding

    private val projectsSharedViewModel: ProjectsViewModel by viewModels({ activity as ViewModelStoreOwner },
        { (activity?.application as GitDroidApplication).appComponent.projectsViewModelFactory() })

    private lateinit var projectsAdapter: ProjectsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProjectsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Init adapter for recycler
        initAdapter()
        // Init observer
        setupObserver(projectsSharedViewModel)

        binding.addBtn.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {

                Log.d(TAG, "In coro scope, before adding project")
                projectsSharedViewModel.addProject(binding.enterNewProjNameEditText.text.toString())
            }

        }
    }

    private fun setupObserver(projectsViewModel: ProjectsViewModel) {
        projectsViewModel.projectList.observe(viewLifecycleOwner) { projectItemsList ->
            if (projectItemsList != null) {
                Log.d(TAG, "Loading from Firebase.")
                projectsAdapter.setList(projectItemsList)
            }
        }
    }

    private fun initAdapter() {
        Log.d(TAG, "initAdapter() called")
        projectsAdapter = ProjectsAdapter(this as ProjectItemClickListener)
        binding.projectsRecycler.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.projectsRecycler.adapter = projectsAdapter

        if (projectsAdapter.itemCount == 0) {
            Log.d(TAG, "Error in Firebase. Loading from local DB (collect flow).")
            lifecycle.coroutineScope.launch {
                projectsSharedViewModel.allProjects().collect {
                    projectsAdapter.setList(it)
                }
            }
        }

    }

    override fun onItemClicked(project: Project) {
        Log.d(MainActivity.TAG, "On item clicked: ${project.projectName}")
        // TODO открыть выпадающий список
    }

    override fun onDeleteClicked(project: Project) {
        Log.d(TAG, "onDeleteClicked() called with: project = $project")
        projectsSharedViewModel.deleteProject(project.projectId)
    }

    companion object {
        const val TAG = "ProjFragLog"

        @JvmStatic
        fun newInstance() =
            ProjectsFragment()
    }
}