package com.example.todomateclone.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.todomateclone.databinding.FragmentLoginBinding
import com.example.todomateclone.util.AuthStorage
import com.example.todomateclone.util.Toaster
import com.example.todomateclone.viewmodel.UserViewModel
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModel()
    private var _binding:FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val authStorage: AuthStorage by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = binding.email
        val password = binding.password
        val loginButton = binding.login
        val loadingProgressBar = binding.loading
        val upButton = binding.upButton


        // when loginButton is clicked, start login
        loginButton.setOnClickListener {
            // show loadingProgressBar
            loadingProgressBar.visibility = View.VISIBLE
            Log.d("userViewModel", "Start Login")

            CoroutineScope(Dispatchers.IO).launch {

                // request server user login with email and password
                userViewModel.login(email.text.toString(), password.text.toString())
                authStorage.authInfo.collect {
                    if (it == null){
                        Log.d("LoginFragment", "Unauthorized error")
                        loadingProgressBar.visibility = View.INVISIBLE
                    } else {
                        Log.d("LoginFragment", "Login Succeeded")
                        loadingProgressBar.visibility = View.INVISIBLE
                        launch(Dispatchers.Main) {
                            navigateToMain()
                        }
                    }
                }
            }
        }

        // navigate up action
        upButton.setOnClickListener {
            this.findNavController().navigateUp()
        }
    }

    private fun navigateToMain() {
        val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
        this.findNavController().navigate(action)
    }
}