package com.example.gitdroid.presentation.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import com.example.gitdroid.data.SessionManager
import com.example.gitdroid.databinding.FragmentAuthBinding
import com.example.gitdroid.domain.AuthRepository
import com.example.gitdroid.domain.AuthRepositoryImpl
import com.example.gitdroid.presentation.MainActivity
import com.example.gitdroid.presentation.misc.navigation
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding

    private lateinit var authRepository: AuthRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAuthBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        authRepository = AuthRepositoryImpl(context, requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.authBtn.setOnClickListener {
            val email = binding.enterEmailEditText.text.toString()
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(context, "Enter your github id", Toast.LENGTH_LONG)
                    .show()
            } else {
                authRepository.signInWithGithubProvider(email)
            }
        }
    }

    companion object {
        const val TAG = "AuthFragmLog"
        @JvmStatic
        fun newInstance() =
            AuthFragment()
    }
}