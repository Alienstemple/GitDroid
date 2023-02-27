package com.example.gitdroid.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.gitdroid.R
import com.example.gitdroid.data.auth.utils.launchAndCollectIn
import com.example.gitdroid.databinding.FragmentAuthBinding
import com.example.gitdroid.databinding.FragmentListIssuesBinding
import com.example.gitdroid.presentation.vm.AuthViewModel
import com.example.gitdroid.presentation.vm.IssuesViewModel
import com.example.gitdroid.presentation.vm.ViewModelFactory
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse

class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding
    private val authViewModel: AuthViewModel by viewModels()

    private val getAuthResponse =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val dataIntent = it.data ?: return@registerForActivityResult
            handleAuthResponseIntent(dataIntent)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAuthBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    private fun bindViewModel() {
        binding.authBtn.setOnClickListener { authViewModel.openLoginPage() }
        authViewModel.loadingFlow.launchAndCollectIn(viewLifecycleOwner) {
            Log.d(TAG, "Loading")
        }
        authViewModel.openAuthPageFlow.launchAndCollectIn(viewLifecycleOwner) {
            openAuthPage(it)
        }
        authViewModel.toastFlow.launchAndCollectIn(viewLifecycleOwner) {
            Log.d(TAG, "Toast flow")
        }
        authViewModel.authSuccessFlow.launchAndCollectIn(viewLifecycleOwner) {
//            findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToRepositoryListFragment())
            Log.d(TAG, "Success! Can use token!")
        }
    }

    //    private fun updateIsLoading(isLoading: Boolean) = with(binding) {
//        loginButton.isVisible = !isLoading
//        loginProgress.isVisible = isLoading
//    }
//
    private fun openAuthPage(intent: Intent) {
        getAuthResponse.launch(intent)
    }

    private fun handleAuthResponseIntent(intent: Intent) {
        // пытаемся получить ошибку из ответа. null - если все ок
        val exception = AuthorizationException.fromIntent(intent)
        // пытаемся получить запрос для обмена кода на токен, null - если произошла ошибка
        val tokenExchangeRequest = AuthorizationResponse.fromIntent(intent)
            ?.createTokenExchangeRequest()
        when {
            // авторизация завершались ошибкой
            exception != null -> authViewModel.onAuthCodeFailed(exception)
            // авторизация прошла успешно, меняем код на токен
            tokenExchangeRequest != null ->
                authViewModel.onAuthCodeReceived(tokenExchangeRequest)
        }
    }

    companion object {
        const val TAG = "AuthFragLog"

        @JvmStatic
        fun newInstance() =
            AuthFragment()
    }
}