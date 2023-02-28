package com.example.gitdroid.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gitdroid.R
import com.example.gitdroid.databinding.FragmentHelloBinding
import com.example.gitdroid.databinding.FragmentListIssuesBinding
import com.example.gitdroid.presentation.misc.navigation

private const val ARG_USERNAME = "userName"
private const val ARG_AVATAR_URL = "avatarUrl"

class HelloFragment : Fragment() {
    private lateinit var binding: FragmentHelloBinding

    private var userName: String? = null
    private var avatarUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userName = it.getString(ARG_USERNAME)
            avatarUrl = it.getString(ARG_AVATAR_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHelloBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchBtn.setOnClickListener {
            navigation().openCodeSearch()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(userName: String, avatarUrl: String) =
            HelloFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USERNAME, userName)
                    putString(ARG_AVATAR_URL, avatarUrl)
                }
            }
    }
}