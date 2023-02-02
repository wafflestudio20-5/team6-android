package com.example.todomateclone.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.todomateclone.R
import com.example.todomateclone.databinding.FragmentSearchUserBinding
import com.example.todomateclone.viewmodel.UserViewModel
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchUserFragment : Fragment() {
//
    private var _binding: FragmentSearchUserBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchUserBinding.inflate(inflater, container, false)
        return binding.root
    }
//
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchButton.setOnClickListener {
            var emailstr = binding.searchEditText.text.toString()
            lifecycleScope.launch {
                viewModel.searchUser(emailstr)
                viewModel.searcheduser.collect {
                    if (it == null) {
                        binding.apply {
                            nullTextView.visibility = VISIBLE
                            userView.visibility = INVISIBLE
                            willFollowButton.visibility = INVISIBLE
                            followingButton.visibility = INVISIBLE
                        }
                    } else {
                        binding.apply {
                            nullTextView.visibility = INVISIBLE
                            userView.visibility = VISIBLE
                            emailTextView.setText(it?.email)
                            nicknameTextView.setText("닉네임: "+it?.nickname)
                        }
                        lifecycleScope.launch {
                            viewModel.checkFollow(viewModel.searcheduser.value!!.id)
                            viewModel.isfollowing.collect {
                                if(it==null) {
                                    binding.apply {
                                        willFollowButton.visibility = INVISIBLE
                                        followingButton.visibility = INVISIBLE
                                    }
                                }
                                else{
                                    binding.apply {
                                        //if not yet following
                                        if(it!!.is_following) {
                                            willFollowButton.visibility = INVISIBLE
                                            followingButton.visibility = VISIBLE
                                        }
                                        else {
                                            willFollowButton.visibility = VISIBLE
                                            followingButton.visibility = INVISIBLE
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
//


        binding.willFollowButton.setOnClickListener {
            lifecycleScope.launch {viewModel.followUser(viewModel.searcheduser.value!!.id)}
            binding.willFollowButton.visibility = INVISIBLE
            binding.followingButton.visibility = VISIBLE
        }

        binding.followingButton.setOnClickListener {
            lifecycleScope.launch {viewModel.unfollowUser(viewModel.searcheduser.value!!.id)}
            binding.willFollowButton.visibility = VISIBLE
            binding.followingButton.visibility = INVISIBLE
        }

        binding.userView.setOnClickListener {
            val sid = viewModel.searcheduser.value!!.id
            Log.d("userView.click", sid.toString()+" "+viewModel.isfollowing.value!!.is_following.toString())
            if(binding.followingButton.visibility == VISIBLE) {
                val action = SearchUserFragmentDirections.actionSearchUserFragmentToTodoListFragment(searchedId = sid)
                this@SearchUserFragment.findNavController().navigate(action)
            }
            else {
                val myToast: Toast = Toast.makeText(
                    this@SearchUserFragment.context,
                    "팔로우하지 않아 열람할 수 없습니다.",
                    Toast.LENGTH_SHORT
                )
                myToast.show()
            }
        }

    }

}