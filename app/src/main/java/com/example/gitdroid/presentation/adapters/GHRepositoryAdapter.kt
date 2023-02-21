package com.example.gitdroid.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gitdroid.models.domain.GHRepository

class GHRepositoryAdapter() :
    RecyclerView.Adapter<GHRepositoryAdapter.ViewHolder>() {

    private val repoList = mutableListOf<GHRepository>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "onCreateViewHolder() called with: parent = $parent, viewType = $viewType")

        val binding = GHRepositoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(repoList[position])
    }

    override fun getItemCount() = repoList.size

    fun setList(newList: List<GHRepository>) {
        repoList.apply {  // TODO DiffUtil
            clear()
            addAll(newList)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(binding: GHRepositoryItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private val tickerItemBinding = binding

        fun bind(tickerItem: TickerOutput) =
            with(tickerItemBinding) {
                Log.d(TAG, "bind() called ${tickerItem.name}")

                tickerNameTv.text = tickerItem.name
                Picasso.get()
                    .load("https://static2.finnhub.io/file/publicdatany/finnhubimage/stock_logo/AAPL.svg")  // tickerItem.logo
                    .into(tickerIcon)
                cTv.text = tickerItem.c.toString()
                dTv.text = tickerItem.d.toString()
                dpTv.text = tickerItem.dp.toString()

                itemView.setOnClickListener {
                    clickListener.onItemClicked(tickerItem)
                }
            }
    }

    companion object {
        const val TAG = "TickAdaptLog"
    }
}