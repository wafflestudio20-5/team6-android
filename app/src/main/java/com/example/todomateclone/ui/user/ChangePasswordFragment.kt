package com.example.todomateclone.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todomateclone.R
import com.example.todomateclone.databinding.FragmentChangePasswordBinding
import com.example.todomateclone.databinding.FragmentUserPageBinding
import com.example.todomateclone.network.dto.UserDTO
import com.example.todomateclone.ui.login.EmailAuthenticateFragmentArgs
import com.example.todomateclone.util.AuthStorage
import com.example.todomateclone.viewmodel.UserDetailViewModel
import com.example.todomateclone.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangePasswordFragment : Fragment() {

    private val navigationArgs: EmailAuthenticateFragmentArgs by navArgs()
    private val userDetailViewModel: UserDetailViewModel by viewModel()
    private val userViewModel: UserViewModel by viewModel()
    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)

        CoroutineScope(Dispatchers.IO).launch {
            lifecycleScope.launchWhenStarted {
                userDetailViewModel.user.collectLatest {
                    userDetailViewModel.getUser()
                }
            }
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val password1TextView = binding.textViewEditPasswordChanged1
        val password2TextView = binding.textViewEditPasswordChanged2
        val certificationTextView = binding.textViewEditCertification
        val submitButton = binding.buttonSubmitEmail


        submitButton.setOnClickListener {
            val code = certificationTextView.text.toString()
            val email = userDetailViewModel.user.value?.email.toString()
            val new_password1 = password1TextView.text.toString()
            val new_password2 = password2TextView.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                userDetailViewModel.confirmPasswordChangeRequest(
                    code = code,
                    email = email,
                    new_password1 = new_password1,
                    new_password2 = new_password2
                )
                launch(Dispatchers.Main) {
                    findNavController().navigateUp()
                }
            }
        }
    }
}