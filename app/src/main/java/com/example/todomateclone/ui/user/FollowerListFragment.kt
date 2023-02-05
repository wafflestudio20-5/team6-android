package com.example.todomateclone.ui.user

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todomateclone.databinding.FragmentFollowerListBinding
import com.example.todomateclone.databinding.FragmentFollowingListBinding
import com.example.todomateclone.databinding.FragmentTodoListBinding
import com.example.todomateclone.network.dto.FollowerDTO
import com.example.todomateclone.network.dto.TaskDTO
import com.example.todomateclone.ui.FolloweeListAdapter
import com.example.todomateclone.ui.FollowerListAdapter
import com.example.todomateclone.ui.SearchUserFragmentDirections
import com.example.todomateclone.ui.todo.TodoFixerFragment
import com.example.todomateclone.ui.TodoListAdapter
import com.example.todomateclone.ui.todo.OnDismissListenerAdder
import com.example.todomateclone.ui.todo.TodoAdderFragment
import com.example.todomateclone.viewmodel.TodoViewModel
import com.example.todomateclone.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FollowerListFragment : Fragment() {
    private lateinit var binding: FragmentFollowerListBinding

    private val viewModel: UserViewModel by viewModel()
    lateinit var adapter: FollowerListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowerListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FollowerListAdapter(
            {follower -> deleteFinal(follower)},
            {follower -> blockFinal(follower)},
            {follower -> followupFinal(follower)},
            {follower -> cancelfollowupFinal(follower)}
        )
        binding.recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.layoutManager = layoutManager
        refreshFollower()

        binding.refreshButton.setOnClickListener {
            refreshFollower()
        }
    }

    fun refreshFollower() {
        viewLifecycleOwner.lifecycleScope.launch {
            val pager = viewModel.createFollowerPager()
            pager.collect { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    fun deleteFinal(follower: FollowerDTO) {
        lifecycleScope.launch {viewModel.deleteFollower(follower.from_user_id)}
        refreshFollower()
    }

    fun blockFinal(follower: FollowerDTO) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setMessage("정말로 차단하시겠습니까?")
            .setPositiveButton("네") { _, _ ->
                lifecycleScope.launch {viewModel.blockUser(follower.from_user_id)}
                refreshFollower()
            }
            .setNegativeButton("아니요") { _, _ ->
                // Perform the action for "No"
            }
        val dialog = builder.create()
        dialog.show()
    }

    fun followupFinal(follower: FollowerDTO) {
        lifecycleScope.launch {viewModel.followUser(follower.from_user_id)}
    }

    fun cancelfollowupFinal(follower: FollowerDTO) {
        lifecycleScope.launch {viewModel.unfollowUser(follower.from_user_id)}
    }

}
