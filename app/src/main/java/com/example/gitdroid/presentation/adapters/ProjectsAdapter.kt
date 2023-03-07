package com.example.gitdroid.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gitdroid.databinding.GhRepositoryItemBinding
import com.example.gitdroid.databinding.ProjectItemBinding
import com.example.gitdroid.models.domain.GHRepository
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.presentation.misc.ProjectItemClickListener
import com.example.gitdroid.presentation.misc.RepositoryItemClickListener

class ProjectsAdapter(private val projectItemClickListener: ProjectItemClickListener) :
    RecyclerView.Adapter<ProjectsAdapter.ViewHolder>() {

    private val projectList = mutableListOf<Project>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "onCreateViewHolder() called with: parent = $parent, viewType = $viewType")

        val binding =
            ProjectItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(projectList[position], projectItemClickListener)
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

        fun bind(projectItem: Project, clickListener: ProjectItemClickListener) =
            with(projectItemBinding) {
                Log.d(TAG, "bind() called ${projectItem.name}")
                projectNameTv.text = projectItem.name

                itemView.setOnClickListener {
                    clickListener.onItemClicked(projectItem)
                }

                shareBtn.setOnClickListener {
                    // TODO share project via email
                    Log.d(TAG, "Share Project via email")
                }

                deleteBtn.setOnClickListener {
                    clickListener.onDeleteClicked(projectItem)
                }
            }
    }

    companion object {
        const val TAG = "ProjAdaptLog"
    }
}