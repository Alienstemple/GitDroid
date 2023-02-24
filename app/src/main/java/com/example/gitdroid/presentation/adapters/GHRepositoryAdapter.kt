package com.example.gitdroid.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gitdroid.databinding.GhRepositoryItemBinding
import com.example.gitdroid.models.domain.GHRepository
import com.example.gitdroid.presentation.misc.RepositoryItemClickListener

class GHRepositoryAdapter(private val repositoryItemClickListener: RepositoryItemClickListener) :
    RecyclerView.Adapter<GHRepositoryAdapter.ViewHolder>() {

    private val repoList = mutableListOf<GHRepository>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "onCreateViewHolder() called with: parent = $parent, viewType = $viewType")

        val binding =
            GhRepositoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(repoList[position], repositoryItemClickListener)
    }

    override fun getItemCount() = repoList.size

    fun setList(newList: List<GHRepository>) {
        repoList.apply {  // TODO DiffUtil
            clear()
            addAll(newList)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(binding: GhRepositoryItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private val tickerItemBinding = binding

        fun bind(repoItem: GHRepository, clickListener: RepositoryItemClickListener) =
            with(tickerItemBinding) {
                Log.d(TAG, "bind() called ${repoItem.name}")
                repoNameTv.text = repoItem.name
                repoLanguageTv.text = repoItem.language
                repoUpdatedTv.text = repoItem.updated_at
                issuesTv.text = "${repoItem.open_issues_count.toString()} issues"
                issuesBtn // TODO onItemClick
                itemView.setOnClickListener {
                    clickListener.onItemClicked(repoItem)
                }
            }
    }

    companion object {
        const val TAG = "GHRepoAdaptLog"
    }
}