package com.example.gitdroid.presentation.adapters

import android.R
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gitdroid.databinding.ProjectItemBinding
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResultItem

class ProjectsAdapter : RecyclerView.Adapter<ProjectsAdapter.ViewHolder>() {

    private val projectList = mutableListOf<Project>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "onCreateViewHolder() called with: parent = $parent, viewType = $viewType")

        val binding =
            ProjectItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(projectList[position])
    }

    override fun getItemCount() = projectList.size

    fun setList(newList: List<Project>) {
        projectList.apply {  // TODO DiffUtil
            clear()
            addAll(newList)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(binding: ProjectItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private val projectItemBinding = binding

        fun bind(projectItem: Project) =
            with(projectItemBinding) {
                Log.d(TAG, "bind() called ${projectItem.projectName}")
                projectNameTv.text = projectItem.projectName

                itemView.setOnClickListener {

                    val searchResItems = projectItem.searchResList

                    if (searchResItems.isNotEmpty()) {
                        openListSearchResults(searchResItems)
                    }
                }
            }

        private fun ProjectItemBinding.openListSearchResults(searchResItems: List<SearchResultItem>) {
            val repoNamesList = mutableListOf<String>()
            searchResItems.map {
                repoNamesList.add(it.ghRepository.repoFullName)
            }

            var listView = projectSearchResList
            val arrayAdapter = ArrayAdapter(itemView.context,
                R.layout.simple_list_item_1, repoNamesList)
            listView.adapter = arrayAdapter

            projectSearchResListScroll.visibility =
                when (projectSearchResListScroll.visibility) {
                    View.VISIBLE -> {
                        View.GONE
                    }
                    View.GONE -> {
                        View.VISIBLE
                    }
                    else -> {
                        View.GONE
                    }
                }
        }
    }

    companion object {
        const val TAG = "ProjForSearAdaptLog"
    }
}