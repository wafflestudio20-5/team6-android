package com.example.todomateclone.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todomateclone.databinding.FragmentLoginBinding
import com.example.todomateclone.viewmodel.UserViewModel
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModel()
    private var _binding:FragmentLoginBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = binding.username
        val password = binding.password
        val loginButton = binding.login
        val loadingProgressBar = binding.loading

        loginButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            Log.d("userViewModel", "Start Login")
            CoroutineScope(Dispatchers.IO).launch {
                userViewModel.login(username.toString(), password.toString())
            }
            loadingProgressBar.visibility = View.INVISIBLE
        }
    }
}