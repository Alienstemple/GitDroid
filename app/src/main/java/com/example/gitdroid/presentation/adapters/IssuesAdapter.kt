package com.example.gitdroid.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gitdroid.databinding.IssueItemBinding
import com.example.gitdroid.models.domain.Issue

class IssuesAdapter :
    RecyclerView.Adapter<IssuesAdapter.ViewHolder>() {

    private val issuesList = mutableListOf<Issue>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "onCreateViewHolder() called with: parent = $parent, viewType = $viewType")

        val binding =
            IssueItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(issuesList[position])
    }

    override fun getItemCount() = issuesList.size

    fun setList(newList: List<Issue>) {
        issuesList.apply {  // TODO DiffUtil
            clear()
            addAll(newList)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(binding: IssueItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private val issueItemBinding = binding

        fun bind(issueItem: Issue) =
            with(issueItemBinding) {
                Log.d(TAG, "bind() called ${issueItem.title}")
                issueTitleTv.text = issueItem.title
                userTv.text = issueItem.body
                issueUpdatedTv.text = issueItem.updated_at
                detailsBtn // TODO onItemClick
            }
    }

    companion object {
        const val TAG = "IssueAdaptLog"
    }
}