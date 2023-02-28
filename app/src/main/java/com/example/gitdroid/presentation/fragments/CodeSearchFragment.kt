package com.example.gitdroid.presentation.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gitdroid.R
import com.example.gitdroid.data.GithubApiService
import com.example.gitdroid.data.NetworkRepositoryImpl
import com.example.gitdroid.data.NetworkService
import com.example.gitdroid.data.SessionManager
import com.example.gitdroid.databinding.FragmentCodeSearchBinding
import com.example.gitdroid.domain.GithubInteractorImpl
import com.example.gitdroid.presentation.adapters.SearchResultAdapter
import com.example.gitdroid.presentation.vm.RepositoryViewModel
import com.example.gitdroid.presentation.vm.ViewModelFactory

class CodeSearchFragment : Fragment() {

    private lateinit var binding: FragmentCodeSearchBinding

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
        val repositoryViewModel: RepositoryViewModel =
            ViewModelProvider(this,
                ViewModelFactory(githubInteractor))[RepositoryViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Init adapter for recycler
//        initAdapter()
//        // Init observer
//        setupObserver(repositoryViewModel)

        // TODO onClick -- chrome custom tab
//        setGetReposByUserOnClickListener(repositoryViewModel)

        binding.goBtn.setOnClickListener {

            val url = "https://github.com/jeremych1000/ee3-dsd/blob/e2fa7010534f350c48a5462967c84161d4e8e72b/T8_combined_func2/hello_world.archive.rpt"  // "https://google.com/"

            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(requireActivity(), Uri.parse(url))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CodeSearchFragment()
    }
}