package com.example.gitdroid.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitdroid.data.GithubApiService
import com.example.gitdroid.data.NetworkRepositoryImpl
import com.example.gitdroid.data.NetworkService
import com.example.gitdroid.data.SessionManager
import com.example.gitdroid.databinding.FragmentCodeSearchBinding
import com.example.gitdroid.domain.GithubInteractorImpl
import com.example.gitdroid.presentation.adapters.SearchResultAdapter
import com.example.gitdroid.presentation.misc.SearchResultItemClickListener
import com.example.gitdroid.presentation.vm.SearchResultViewModel
import com.example.gitdroid.presentation.vm.GithubViewModelFactory

class CodeSearchFragment : Fragment() {

    private lateinit var binding: FragmentCodeSearchBinding

    private lateinit var searchResultViewModel: SearchResultViewModel
    private lateinit var searchResultAdapter: SearchResultAdapter

    private lateinit var networkService: NetworkService
    private lateinit var networkRepository: NetworkRepositoryImpl
    private lateinit var githubInteractor: GithubInteractorImpl

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCodeSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        networkService = NetworkService(GithubApiService.getInstance(), SessionManager(context))
        networkRepository = NetworkRepositoryImpl(networkService)
        githubInteractor = GithubInteractorImpl(networkRepository)
        searchResultViewModel =
            ViewModelProvider(this,
                GithubViewModelFactory(githubInteractor))[SearchResultViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Init adapter for recycler
        initAdapter()
        // Init observer
        setupObserver(searchResultViewModel)

        binding.goBtn.setOnClickListener {
            searchResultViewModel.getCodeSearch(binding.enterSearchQueryEditText.text.toString())
        }
    }

    private fun setupObserver(searchResultViewModel: SearchResultViewModel) {
        searchResultViewModel.searchResultItems.observe(viewLifecycleOwner) { searchResItemsList ->
            searchResItemsList?.let {
                // Обновляем Recycler View
                searchResultAdapter.setList(it)
            }
        }
    }

    private fun initAdapter() {
        searchResultAdapter = SearchResultAdapter(activity as SearchResultItemClickListener)
        binding.searchResultsRecycler.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.searchResultsRecycler.adapter = searchResultAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CodeSearchFragment()
    }
}