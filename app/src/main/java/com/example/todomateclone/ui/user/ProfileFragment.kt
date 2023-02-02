package com.example.todomateclone.ui.user
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.todomateclone.databinding.FragmentProfileBinding
import com.example.todomateclone.network.dto.UserDTO
import com.example.todomateclone.viewmodel.UserDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private val userDetailViewModel: UserDetailViewModel by viewModel()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        CoroutineScope(Dispatchers.IO).launch {
            lifecycleScope.launchWhenStarted {
                userDetailViewModel.user.collectLatest {
                    userDetailViewModel.getUser()
                    binding.textViewEditNickname.setText(userDetailViewModel.user.value?.nickname.toString())
                    binding.textViewEditDetail.setText(userDetailViewModel.user.value?.detail.toString())
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editNicknameTextview = binding.textViewEditNickname
        val editDetailTextview = binding.textViewEditDetail
        val submitButton = binding.buttonSubmit

        submitButton.setOnClickListener {
            val newNickname = editNicknameTextview.text.toString()
            val newDetail = editDetailTextview.text.toString()
            val id = userDetailViewModel.user.value?.id!!.toInt()
            val email = userDetailViewModel.user.value?.email.toString()
            val userDTO : UserDTO = UserDTO(id= id , email= email, nickname = newNickname, detail = newDetail)

            CoroutineScope(Dispatchers.IO).launch {
                lifecycleScope.launchWhenStarted {
                    userDetailViewModel.user.collectLatest {
                        userDetailViewModel.updateUser(userDTO)
                        launch(Dispatchers.Main){findNavController().navigateUp()}
                    }
                }
            }
        }
    }
}