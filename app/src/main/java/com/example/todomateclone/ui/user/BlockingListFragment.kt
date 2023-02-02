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
import com.example.todomateclone.databinding.FragmentBlockingListBinding
import com.example.todomateclone.databinding.FragmentFollowingListBinding
import com.example.todomateclone.databinding.FragmentTodoListBinding
import com.example.todomateclone.network.dto.BlockingDTO
import com.example.todomateclone.network.dto.FolloweeDTO
import com.example.todomateclone.network.dto.FollowerDTO
import com.example.todomateclone.network.dto.TaskDTO
import com.example.todomateclone.ui.BlockingListAdapter
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

class BlockingListFragment : Fragment() {
    private lateinit var binding: FragmentBlockingListBinding

    private val viewModel: UserViewModel by viewModel()
    lateinit var adapter: BlockingListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlockingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = BlockingListAdapter({blocking -> unblockFinal(blocking)})
        binding.recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.layoutManager = layoutManager
        refreshBlocking()

        binding.refreshButton.setOnClickListener {
            refreshBlocking()
        }
    }

    fun refreshBlocking() {
        viewLifecycleOwner.lifecycleScope.launch {
            val pager = viewModel.createBlockingPager()
            pager.collect { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    fun unblockFinal(blocking: BlockingDTO) {
        lifecycleScope.launch {viewModel.unblockUser(blocking.to_user_id)}
        refreshBlocking()
    }

}
