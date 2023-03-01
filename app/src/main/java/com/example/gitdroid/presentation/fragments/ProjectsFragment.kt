package com.example.gitdroid.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitdroid.data.*
import com.example.gitdroid.databinding.FragmentProjectsBinding
import com.example.gitdroid.domain.ProjectsFirebaseRepository
import com.example.gitdroid.domain.ProjectsInteractor
import com.example.gitdroid.domain.ProjectsInteractorImpl
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.presentation.adapters.ProjectsAdapter
import com.example.gitdroid.presentation.misc.ProjectItemClickListener
import com.example.gitdroid.presentation.vm.ProjectsViewModel
import com.example.gitdroid.presentation.vm.ProjectsViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProjectsFragment : Fragment() {

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
            // TODO move to VM
//            projectsViewModel.addNewProject(binding.enterNewProjNameEditText.text.toString())

            val firstProject = Project("firstProject", listOf("result 1", "result 2", "result 3"))
            val firebaseRepository = ProjectsFirebaseRepositoryImpl()

            CoroutineScope(Dispatchers.IO).launch {
                Log.d(TAG, "In coro scope, before adding project")
//                firebaseRepository.addProject(firstProject)
                projectsViewModel.addProject(binding.enterNewProjNameEditText.text.toString())
            }

        }
    }

    private fun setupObserver(projectsViewModel: ProjectsViewModel) {
        projectsViewModel.projectList.observe(viewLifecycleOwner) { projectItemsList ->
            projectItemsList?.let {
                // Обновляем Recycler View
//                projectsAdapter.setList(it)
            }
        }
    }

    private fun initAdapter() {
//        projectsAdapter = ProjectsAdapter(activity as ProjectItemClickListener)
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