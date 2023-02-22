package com.example.gitdroid.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitdroid.R
import com.example.gitdroid.data.GithubApiService
import com.example.gitdroid.data.NetworkRepositoryImpl
import com.example.gitdroid.data.NetworkService
import com.example.gitdroid.databinding.FragmentFindReposByUserBinding
import com.example.gitdroid.databinding.FragmentListIssuesBinding
import com.example.gitdroid.domain.GithubInteractorImpl
import com.example.gitdroid.presentation.adapters.GHRepositoryAdapter
import com.example.gitdroid.presentation.adapters.IssuesAdapter
import com.example.gitdroid.presentation.vm.IssuesViewModel
import com.example.gitdroid.presentation.vm.RepositoryViewModel
import com.example.gitdroid.presentation.vm.ViewModelFactory

class ListIssuesFragment : Fragment() {

    private lateinit var binding: FragmentListIssuesBinding
    private lateinit var issuesAdapter: IssuesAdapter

    private val networkService = NetworkService(GithubApiService.getInstance())

    private val networkRepository = NetworkRepositoryImpl(networkService)
    private val githubInteractor = GithubInteractorImpl(networkRepository)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentListIssuesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val issuesViewModel: IssuesViewModel =
            ViewModelProvider(this,
                ViewModelFactory(githubInteractor))[IssuesViewModel::class.java]
        // Init adapter for recycler
        initAdapter()
        // Init observer
        setupObserver(issuesViewModel)

        setGetIssuesByUserAndRepositoryOnClickListener(issuesViewModel)
    }

    private fun setGetIssuesByUserAndRepositoryOnClickListener(repositoryViewModel: RepositoryViewModel) = with(binding) {
        getBtn.setOnClickListener {
            repositoryViewModel.getReposByUser(userNameEditText.text.toString())
        }
    }

    private fun setupObserver(issuesViewModel: IssuesViewModel) {
        issuesViewModel.issueList.observe(viewLifecycleOwner) { repoList ->
            repoList?.let {
                // Обновляем Recycler View
                issuesAdapter.setList(it)
            }
        }
    }

    private fun initAdapter() {
        issuesAdapter = IssuesAdapter()
        binding.repoListRecycler.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.repoListRecycler.adapter = issuesAdapter
    }

    companion object {
        const val TAG = "IssuesFragLog"

        @JvmStatic
        fun newInstance() =
            ListIssuesFragment()
    }
}