package com.example.todomateclone.ui.user

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
import com.example.todomateclone.databinding.FragmentFollowingListBinding
import com.example.todomateclone.databinding.FragmentTodoListBinding
import com.example.todomateclone.network.dto.TaskDTO
import com.example.todomateclone.ui.FolloweeListAdapter
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

class FollowingListFragment : Fragment() {
    private lateinit var binding: FragmentFollowingListBinding

    private val viewModel: UserViewModel by viewModel()
    lateinit var adapter: FolloweeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FolloweeListAdapter({followee -> navigateTask(followee.to_user_id)})
        binding.recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.layoutManager = layoutManager
        refreshFollowee()

        binding.refreshButton.setOnClickListener {
            refreshFollowee()
        }
    }

    fun refreshFollowee() {
        viewLifecycleOwner.lifecycleScope.launch {
            val pager = viewModel.createFolloweePager()
            pager.collect { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    fun navigateTask(sid: Int) {
        val action = FollowingListFragmentDirections.actionFollowingListFragmentToTodoListFragment(searchedId = sid)
        this.findNavController().navigate(action)
    }

}
