package com.example.todomateclone.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todomateclone.R
import com.example.todomateclone.databinding.FragmentStartBinding
import com.example.todomateclone.viewmodel.UserViewModel

class StartFragment : Fragment() {

//    private val userViewModel: UserViewModel
    private var _binding:FragmentStartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginButton = binding.loginButton
        val guestButton = binding.guestButton
        val signUpText = binding.signUpText

        loginButton.setOnClickListener(){
            val action = StartFragmentDirections.actionStartFragmentToLoginFragment()
            this.findNavController().navigate(action)
        }

        guestButton.setOnClickListener(){
            // todo: add navigate action to main page
        }

        signUpText.setOnClickListener(){
            val action = StartFragmentDirections.actionStartFragmentToSignUpFragment()
            this.findNavController().navigate(action)
        }
    }


}