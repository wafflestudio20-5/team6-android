package com.example.todomateclone.ui

import android.os.Bundle
import android.service.voice.VoiceInteractionSession.VisibleActivityCallback
import android.util.Log
import android.view.*
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import com.example.todomateclone.MainActivity
import com.example.todomateclone.R
import com.example.todomateclone.databinding.FragmentMainBinding
import com.example.todomateclone.databinding.FragmentSearchUserBinding
import com.example.todomateclone.util.AuthStorage
import com.example.todomateclone.viewmodel.TodoViewModel
import com.example.todomateclone.viewmodel.UserViewModel
import com.google.android.material.navigation.NavigationView
import com.kakao.auth.StringSet.access_token
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import org.koin.android.ext.android.inject
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
                            nicknameTextView.setText(it?.nickname)
                        }
                    }
                }
            }
        }
//
////                if(viewModel.searcheduser.value!=null) {
////                    viewModel.checkFollow(viewModel.searcheduser.value!!.id)
////                    viewModel.isfollowing.collect {
////                        if(it==null) {
////                            binding.apply {
////                                willFollowButton.visibility = INVISIBLE
////                                followingButton.visibility = INVISIBLE
////                            }
////                        }
////                        else{
////                            binding.apply {
////                                //if not yet following
////                                if(it!!.is_following) {
////                                    willFollowButton.visibility = INVISIBLE
////                                    followingButton.visibility = VISIBLE
////                                }
////                                else {
////                                    willFollowButton.visibility = VISIBLE
////                                    followingButton.visibility = INVISIBLE
////                                }
////                            }
////                        }
////                    }
////                }
////
//
//            }
//        }
//
        binding.willFollowButton.setOnClickListener {
            viewModel.followUser(viewModel.searcheduser.value!!.id)
            binding.willFollowButton.visibility = INVISIBLE
            binding.followingButton.visibility = VISIBLE
        }

        binding.followingButton.setOnClickListener {
            //viewModel.unfollowUser(viewModel.searcheduser.value!!.id)
            binding.willFollowButton.visibility = INVISIBLE
            binding.followingButton.visibility = VISIBLE
        }

        binding.userView.setOnClickListener {
            val sid = viewModel.searcheduser.value!!.id
            val action = SearchUserFragmentDirections.actionSearchUserFragmentToTodoListFragment(searchedId = sid)
            this.findNavController().navigate(action)

        }

    }
}