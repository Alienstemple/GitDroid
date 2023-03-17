package com.example.gitdroid.presentation.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelStoreOwner
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitdroid.GitDroidApplication
import com.example.gitdroid.R
import com.example.gitdroid.databinding.FragmentProjectsBinding
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.presentation.adapters.ProjectsAdapter
import com.example.gitdroid.presentation.misc.ProjectItemClickListener
import com.example.gitdroid.models.domain.ProjectLoadState
import com.example.gitdroid.presentation.vm.projects.ProjectsViewModel

/**
 * Фрагмент списка проектов
 */
class ProjectsFragment : Fragment(), ProjectItemClickListener {

    private lateinit var binding: FragmentProjectsBinding

    private val projectsSharedViewModel: ProjectsViewModel by viewModels({ activity as ViewModelStoreOwner },
        { (activity?.application as GitDroidApplication).appComponent.projectsViewModelFactory() })

    private lateinit var projectsAdapter: ProjectsAdapter

    private lateinit var prefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProjectsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        // Init adapter for recycler
        initAdapter()
        // Init observer
        setupObserver()
        setupUiStateObserver()

        // Load all projects
        projectsSharedViewModel.loadAllProjects()

        setAddOnClick()
    }

    private fun setupUiStateObserver() {
        projectsSharedViewModel.projectLoadState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                ProjectLoadState.LOADING -> {
                    binding.projectsRecycler.visibility = View.GONE
                    binding.projListProgress.visibility = View.VISIBLE
                }
                ProjectLoadState.ERROR -> {
                    binding.projectsRecycler.visibility = View.GONE
                    binding.projListProgress.visibility = View.GONE
                    showErrorAlertDialog()
                }
                ProjectLoadState.COMPLETED -> {
                    binding.projectsRecycler.visibility = View.VISIBLE
                    binding.projListProgress.visibility = View.GONE
                }
                null -> {
                    binding.projectsRecycler.visibility = View.GONE
                    binding.projListProgress.visibility = View.GONE
                }
            }
        }
    }

    private fun showErrorAlertDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Caution")
        builder.setMessage("Error while loading projects")

        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun setAddOnClick() {
        binding.addBtn.setOnClickListener {
            projectsSharedViewModel.addProject(binding.enterNewProjNameEditText.text.toString())
        }
    }

    override fun onDeleteClicked(project: Project) {
        projectsSharedViewModel.deleteProject(project.projectId)
    }

    override fun onShareClicked(project: Project) {
        val userEmail: String = prefs.getString(getString(R.string.email), "").toString()

        if (userEmail.isEmpty()) {
            Toast.makeText(requireContext(),
                "Please, specify your email in settings (menu)",
                Toast.LENGTH_LONG)
                .show()
        } else {
            sendProjectByEmail(project, userEmail)
        }
    }

    private fun sendProjectByEmail(project: Project, userEmail: String) {

        var projectMessage = "GitDroidProject: ${project.projectName}"
        if (project.searchResList.isNotEmpty()) {
            project.searchResList.map {
                projectMessage += "\n\t${it.ghRepository.repoFullName}\n\t${it.htmlFileUrl}\n"
            }
        }

        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_EMAIL, userEmail)
        intent.putExtra(Intent.EXTRA_SUBJECT, "GitDroid project")
        intent.putExtra(Intent.EXTRA_TEXT, projectMessage)
        intent.type = "message/rfc822"
        startActivity(Intent.createChooser(intent, "Select email"))
    }

    private fun setupObserver() {
        projectsSharedViewModel.projectList.observe(viewLifecycleOwner) { projectItemsList ->
            if (projectItemsList != null) {
                projectsAdapter.setList(projectItemsList)
            }
        }
    }

    private fun initAdapter() {
        projectsAdapter = ProjectsAdapter(this as ProjectItemClickListener)
        binding.projectsRecycler.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.projectsRecycler.adapter = projectsAdapter

    }

    companion object {
        const val TAG = "ProjFragLog"

        @JvmStatic
        fun newInstance() =
            ProjectsFragment()
    }
}