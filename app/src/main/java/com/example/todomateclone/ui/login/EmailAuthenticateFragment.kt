package com.example.todomateclone.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todomateclone.databinding.FragmentEmailAuthenticateBinding
import com.example.todomateclone.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class EmailAuthenticateFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModel()
    private var _binding: FragmentEmailAuthenticateBinding? = null
    private val binding get() = _binding!!

    private val navigationArgs: EmailAuthenticateFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEmailAuthenticateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // put user email on emailEditText
        binding.email.setText(navigationArgs.email)

        val email = binding.email
        val signupButton = binding.signupButton
        val resendButton = binding.emailResend
        val upButton = binding.upButton

        // if signupButton is clicked, navigate to login fragment
        signupButton.setOnClickListener {
            val action = EmailAuthenticateFragmentDirections.actionEmailAuthenticateFragmentToLoginFragment()
            this.findNavController().navigate(action)
        }

        // if resendButton is clicked, resend email authentication link to user email
        resendButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                userViewModel.resendEmail(email.text.toString())
            }
        }

        // navigate up action
        upButton.setOnClickListener {
            this.findNavController().navigateUp()
        }
    }

}