package com.example.gitdroid.presentation.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.gitdroid.data.SessionManager
import com.example.gitdroid.databinding.FragmentAuthBinding
import com.example.gitdroid.presentation.MainActivity
import com.example.gitdroid.presentation.misc.navigation
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*

class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding

    private lateinit var firebaseUser: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private val provider = OAuthProvider.newBuilder("github.com")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAuthBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authWithFirebase()
    }

    private fun authWithFirebase() = with(binding) {
        // Authorization
        auth = FirebaseAuth.getInstance()

        provider.addCustomParameter("login", enterEmailEditText.text.toString())

        val scopes: ArrayList<String?> = object : ArrayList<String?>() {
            init {
                add("user:email")
            }
        }
        provider.scopes = scopes

        authBtn.setOnClickListener {
            if (TextUtils.isEmpty(enterEmailEditText.text.toString())) {
                Toast.makeText(context, "Enter your github id", Toast.LENGTH_LONG)
                    .show()
            } else {
                signInWithGithubProvider()
            }
        }
    }

    private fun signInWithGithubProvider() {

        // There's something already here! Finish the sign-in for your user.
        val pendingResultTask: Task<AuthResult>? = auth.pendingAuthResult
        if (pendingResultTask != null) {
            pendingResultTask
                .addOnSuccessListener {
                    // User is signed in.
                    Toast.makeText(context, "User exist", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    // Handle failure.
                    Toast.makeText(context, "Error : $it", Toast.LENGTH_LONG).show()
                    Log.d(MainActivity.TAG, "Error : $it")
                }
        } else {

            auth.startActivityForSignInWithProvider( /* activity= */requireActivity(),
                provider.build())
                .addOnSuccessListener(
                    OnSuccessListener<AuthResult?> {
                        // User is signed in.
                        // retrieve the current user
                        firebaseUser = auth.currentUser!!

                        val accessToken = (it.credential as OAuthCredential).accessToken
                        val idToken = (it.credential as OAuthCredential).idToken
                        Log.d(MainActivity.TAG, "Access token = $accessToken")
                        Log.d(MainActivity.TAG, "Id token = $idToken")

                        // Save access token in shared prefs
                        SessionManager(requireContext()).saveAuthToken(accessToken.toString())

                        // TODO add avatar url
                        navigation().openHello(firebaseUser.displayName.toString(), "")

                        Toast.makeText(context, "Login Successfully", Toast.LENGTH_LONG).show()
                    })
                .addOnFailureListener(
                    OnFailureListener {
                        // Handle failure.
                        Toast.makeText(context, "Error : $it", Toast.LENGTH_LONG).show()
                        Log.d(MainActivity.TAG, "Error : $it")
                    })
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AuthFragment()
    }
}