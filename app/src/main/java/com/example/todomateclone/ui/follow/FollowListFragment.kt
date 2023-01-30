package com.example.todomateclone.ui.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todomateclone.databinding.FragmentFollowListBinding
import com.example.todomateclone.viewmodel.FollowViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel




class FollowListFragment : Fragment() {

    private var _binding: FragmentFollowListBinding? = null
    private val binding get() = _binding!!
    private val followViewModel: FollowViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myProfileButton = binding.myProfileButton
        val forwardButton = binding.forwardButton
        val manager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        val adapter = FollowListAdapter(
            onItemClicked = {

                // 다른 사람 계정페이지로 가는 코드
                // val action = DiaryListFragmentDirections.actionDiaryListFragmentToDiaryDetailFragment(it.id)
                // this.findNavController().navigate(action)
            }

        )
        binding.followRecyclerView.layoutManager = manager
        binding.followRecyclerView.adapter = adapter
        binding.followRecyclerView.layoutManager = LinearLayoutManager(this.context)

        /* show entire followList
        lifecycleScope.launch {
            followViewModel.getFollowList()
            followViewModel.followList.collect {
                adapter.submitList(it)
            }
        }*/

        forwardButton.setOnClickListener {
            // 팔로우 탐색하는 곳으로 가는 코드
        }

    }

}