package com.example.gitdroid.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gitdroid.databinding.ProjectItemForSearchBinding
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.presentation.misc.ProjectItemClickListener

class ProjectsForSearchAdapter(private val projectItemClickListener: ProjectItemClickListener) :
    RecyclerView.Adapter<ProjectsForSearchAdapter.ViewHolder>() {

    private val projectList = mutableListOf<Project>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            ProjectItemForSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    class ViewHolder(binding: ProjectItemForSearchBinding) : RecyclerView.ViewHolder(binding.root) {

        private val projectItemBinding = binding

        fun bind(projectItem: Project, clickListener: ProjectItemClickListener) =
            with(projectItemBinding) {
                projectNameTv.text = projectItem.projectName

                itemView.setOnClickListener {
                    clickListener.onItemClicked(projectItem)
                }
            }
    }

    companion object {
        const val TAG = "ProjAdaptLog"
    }
}