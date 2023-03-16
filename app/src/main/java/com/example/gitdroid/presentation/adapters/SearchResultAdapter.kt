package com.example.gitdroid.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gitdroid.databinding.SearchResultItemBinding
import com.example.gitdroid.models.domain.SearchResultItem
import com.example.gitdroid.presentation.misc.SearchResultItemClickListener

class SearchResultAdapter(private val searchResultItemClickListener: SearchResultItemClickListener) :
    RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    private val searchResItemList = mutableListOf<SearchResultItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            SearchResultItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(searchResItemList[position], searchResultItemClickListener)
    }

    override fun getItemCount() = searchResItemList.size

    fun setList(newList: List<SearchResultItem>) {
        searchResItemList.apply {
            clear()
            addAll(newList)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(binding: SearchResultItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private val searchResItemBinding = binding

        fun bind(searchResultItem: SearchResultItem, clickListener: SearchResultItemClickListener) =
            with(searchResItemBinding) {
                resultRepoNameTv.text = searchResultItem.ghRepository.repoName
                addToProjectBtn.setOnClickListener {
                    clickListener.onAddToProjectClicked(searchResultItem)
                }
                itemView.setOnClickListener {
                    clickListener.onItemClicked(searchResultItem)
                }
            }
    }

    companion object {
        const val TAG = "SearcResAdaptLog"
    }
}