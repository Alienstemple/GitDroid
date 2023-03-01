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
import com.example.gitdroid.databinding.FragmentFindReposByUserBinding
import com.example.gitdroid.domain.GithubInteractorImpl
import com.example.gitdroid.presentation.adapters.GHRepositoryAdapter
import com.example.gitdroid.presentation.misc.RepositoryItemClickListener
import com.example.gitdroid.presentation.vm.RepositoryViewModel
import com.example.gitdroid.presentation.vm.GithubViewModelFactory

class FindReposByUserFragment : Fragment() {

    private lateinit var binding: FragmentFindReposByUserBinding
    private lateinit var ghRepositoryAdapter: GHRepositoryAdapter

    private val networkService = NetworkService(GithubApiService.getInstance(), SessionManager(requireContext()))

    private val networkRepository = NetworkRepositoryImpl(networkService)
    private val githubInteractor = GithubInteractorImpl(networkRepository)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFindReposByUserBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repositoryViewModel: RepositoryViewModel =
            ViewModelProvider(this,
                GithubViewModelFactory(githubInteractor))[RepositoryViewModel::class.java]
        // Init adapter for recycler
        initAdapter()
        // Init observer
        setupObserver(repositoryViewModel)

        setGetReposByUserOnClickListener(repositoryViewModel)
    }

    private fun setGetReposByUserOnClickListener(repositoryViewModel: RepositoryViewModel) = with(binding) {
        getBtn.setOnClickListener {
            repositoryViewModel.getReposByUser(userNameEditText.text.toString())
        }
    }

    private fun setupObserver(repositoryViewModel: RepositoryViewModel) {
        repositoryViewModel.repoList.observe(viewLifecycleOwner) { repoList ->
            repoList?.let {
                // Обновляем Recycler View
                ghRepositoryAdapter.setList(it)
            }
        }
    }

    private fun initAdapter() {
        ghRepositoryAdapter = GHRepositoryAdapter(activity as RepositoryItemClickListener)
        binding.repoListRecycler.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.repoListRecycler.adapter = ghRepositoryAdapter
    }

    companion object {
        const val TAG = "FindRepByUserFragLog"

        @JvmStatic
        fun newInstance() =
            FindReposByUserFragment()
    }
}