package com.example.gitdroid.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitdroid.data.GithubApiService
import com.example.gitdroid.data.NetworkRepositoryImpl
import com.example.gitdroid.data.NetworkService
import com.example.gitdroid.data.SessionManager
import com.example.gitdroid.databinding.FragmentListIssuesBinding
import com.example.gitdroid.domain.GithubInteractorImpl
import com.example.gitdroid.presentation.adapters.IssuesAdapter
import com.example.gitdroid.presentation.vm.IssuesViewModel
import com.example.gitdroid.presentation.vm.GithubViewModelFactory

private const val ARG_PARAM1 = "user"
private const val ARG_PARAM2 = "repository"

class ListIssuesFragment : Fragment() {

    private lateinit var user: String
    private lateinit var repository: String

    private lateinit var binding: FragmentListIssuesBinding
    private lateinit var issuesAdapter: IssuesAdapter

    private val networkService = NetworkService(GithubApiService.getInstance(), SessionManager(requireContext()))

    private val networkRepository = NetworkRepositoryImpl(networkService)
    private val githubInteractor = GithubInteractorImpl(networkRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.getString(ARG_PARAM1).toString()
            repository = it.getString(ARG_PARAM2).toString()
        }
    }

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
                GithubViewModelFactory(githubInteractor))[IssuesViewModel::class.java]
        // Init adapter for recycler
        initAdapter()
        // Init observer
        setupObserver(issuesViewModel)

        setGetIssuesByUserAndRepositoryOnClickListener(issuesViewModel)
    }

    private fun setGetIssuesByUserAndRepositoryOnClickListener(issuesViewModel: IssuesViewModel) =
        with(binding) {

            issuesViewModel.getIssueByUserAndRepository(user, repository)

//            getBtn.setOnClickListener {
//                issuesViewModel.getIssueByUserAndRepository(userNameEditText.text.toString(),
//                    repoEditText.text.toString())
//            }
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
        binding.issueListRecycler.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.issueListRecycler.adapter = issuesAdapter
    }

    companion object {
        const val TAG = "IssuesFragLog"

        @JvmStatic
        fun newInstance(user: String, repository: String) =
            ListIssuesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, user)
                    putString(ARG_PARAM2, repository)
                }
            }
    }
}