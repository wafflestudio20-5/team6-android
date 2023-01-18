package com.example.todomateclone.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.todomateclone.R
import com.example.todomateclone.databinding.FragmentStartBinding
import com.example.todomateclone.databinding.FragmentUserPageBinding
import com.example.todomateclone.network.dto.UserDTO
import com.example.todomateclone.util.AuthStorage
import com.example.todomateclone.viewmodel.UserDetailViewModel
import com.example.todomateclone.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserPageFragment : Fragment() {

    private val authStorage: AuthStorage by inject()
    private val userDetailViewModel: UserDetailViewModel by viewModel()
    private val userViewModel: UserViewModel by viewModel()
    private var _binding: FragmentUserPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserPageBinding.inflate(inflater, container, false)

        CoroutineScope(Dispatchers.IO).launch {
            userDetailViewModel.getUser() }

        CoroutineScope(Dispatchers.IO).launch {
            lifecycleScope.launchWhenStarted {
                userDetailViewModel.user.collectLatest {
                    binding.textViewEmail.text = it?.email.toString()
                }
            }
        }
            return binding.root
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileButton = binding.buttonProfile
        val changeEmailButton = binding.buttonChangeEmail
        val changePasswordButton = binding.buttonChangePassword
        val deleteAccountButton = binding.buttonDeleteAccount


        profileButton.setOnClickListener {
            val action = UserPageFragmentDirections.actionUserPageFragmentToProfileFragment()
            this.findNavController().navigate(action)
        }

        changeEmailButton.setOnClickListener(){
            val action = UserPageFragmentDirections.actionUserPageFragmentToChangeEmailFragment()
            this.findNavController().navigate(action)
        }

        changePasswordButton.setOnClickListener(){
            // 비밀번호 변경을 위한 이메일 전송
        }
        deleteAccountButton.setOnClickListener(){
            // 확인을 위한 팝업창 출력

            // 계정삭제 실행, 로그아웃 실행
            CoroutineScope(Dispatchers.IO).launch{
                userViewModel.deleteUser()
                userViewModel.logout()
            }
            val action = UserPageFragmentDirections.actionUserPageFragmentToStartFragment()
            this.findNavController().navigate(action)

        }

    }


}