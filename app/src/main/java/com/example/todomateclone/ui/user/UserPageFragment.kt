package com.example.todomateclone.ui.user

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.todomateclone.R
import com.example.todomateclone.databinding.FragmentUserPageBinding
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

    private val userDetailViewModel: UserDetailViewModel by viewModel()
    private val userViewModel: UserViewModel by viewModel()
    private var _binding: FragmentUserPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserPageBinding.inflate(inflater, container, false)

        CoroutineScope(Dispatchers.IO).launch {

            lifecycleScope.launchWhenStarted {
                userDetailViewModel.user.collectLatest {
                    userDetailViewModel.getUser()
                    binding.textViewEmail.text = userDetailViewModel.user.value?.email.toString()
                }
            }
        }
            return binding.root
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val profileButton = binding.buttonProfile
        val changePasswordButton = binding.buttonChangePassword
        val deleteAccountButton = binding.buttonDeleteAccount


        profileButton.setOnClickListener {
            val action = UserPageFragmentDirections.actionUserPageFragmentToProfileFragment()
            this.findNavController().navigate(action)
        }

        changePasswordButton.setOnClickListener(){
            val email = userDetailViewModel.user.value?.email.toString()
            CoroutineScope(Dispatchers.IO).launch{
                userDetailViewModel.sendResetEmail(email)
            }
            val action = UserPageFragmentDirections.actionUserPageFragmentToChangePasswordFragment()
            this.findNavController().navigate(action)
        }
        deleteAccountButton.setOnClickListener(){
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("계정삭제")
                .setMessage("정말로 계정을 삭제할까요?")
                .setPositiveButton("확인",
                DialogInterface.OnClickListener{ dialog, which ->
                    CoroutineScope(Dispatchers.IO).launch{
                        userViewModel.deleteUser()
                        userViewModel.logout()
                    }
                })
                .setNegativeButton("취소",
                DialogInterface.OnClickListener{dialog, which -> Unit  })
            }
            this.findNavController().navigate(R.id.action_global_login_graph)
        }
}