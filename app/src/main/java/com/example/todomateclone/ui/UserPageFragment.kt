package com.example.todomateclone.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.todomateclone.R
import com.example.todomateclone.databinding.FragmentStartBinding
import com.example.todomateclone.databinding.FragmentUserPageBinding
import com.example.todomateclone.network.dto.UserDTO
import com.example.todomateclone.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserPageFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModel()
    private var _binding: FragmentUserPageBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileButton = binding.buttonProfile
        val changeEmailButton = binding.buttonChangeEmail
        val changePasswordButton = binding.buttonChangePassword
        val deleteAccountButton = binding.buttonDeleteAccount
        val EmailTextView = binding.textViewLinkedEmail


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
            // 확인시 계정삭제 취소시 뒤로가기
        }

    }


}