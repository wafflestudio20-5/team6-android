package com.example.todomateclone.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.todomateclone.R
import com.example.todomateclone.databinding.FragmentChangeEmailBinding
import com.example.todomateclone.viewmodel.UserViewModel

class ChangeEmailFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModel()
    private var _binding: FragmentChangeEmailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val submitEmailButton = binding.buttonSubmitEmail

        submitEmailButton.setOnClickListener(){
            // api통신으로 서버데이터 바꾸고
            // viewModel로 현재 ui로 보여지고 있는 데이터도 바꿔야겠지?
            val action = ChangeEmailFragmentDirections.actionChangeEmailFragmentToUserPageFragment()
            this.findNavController().navigate(action)
        }

    }

}