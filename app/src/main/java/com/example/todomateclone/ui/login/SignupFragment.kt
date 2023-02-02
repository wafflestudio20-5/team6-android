package com.example.todomateclone.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.todomateclone.databinding.FragmentSignupBinding
import com.example.todomateclone.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SignupFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModel()
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = binding.email
        val password1 = binding.password1
        val password2 = binding.password2
        val signUpButton = binding.signup
        val loadingProgressBar = binding.loading
        val upButton = binding.upButton

        signUpButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            lifecycleScope.launch{
                userViewModel.signup(email.text.toString(), password1.text.toString(), password2.text.toString())
                loadingProgressBar.visibility = View.INVISIBLE
                val action = SignupFragmentDirections.actionSignUpFragmentToEmailAuthenticateFragment(email.text.toString())
                findNavController().navigate(action)
            }
        }
        // navigate up action
        upButton.setOnClickListener {
            this.findNavController().navigateUp()
        }
    }

}