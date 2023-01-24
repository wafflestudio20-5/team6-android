package com.example.todomateclone.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.todomateclone.databinding.FragmentTodoAdderBinding
import com.example.todomateclone.viewmodel.TodoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate

class TodoAdderFragment : Fragment() {
    private lateinit var binding: FragmentTodoAdderBinding

    private val viewModel: TodoViewModel by viewModel()
    private val navigationArgs: TodoAdderFragmentArgs by navArgs()



//    private fun isEntryValid(): Boolean {
//        return viewModel.isEntryValid(
//            binding.writeTitle.text.toString(),
//            binding.writeContent.text.toString()
//        )
//    }


//    private fun addNewTodo() {
//        if (isEntryValid()) {
//            viewModel.addNewTodo(
//                binding.writeTitle.text.toString(),
//                binding.writeContent.text.toString()
//            )
//        }
//        val action = TodoAdderFragmentDirections.actionTodoAdderFragmentToTodoListFragment(0)
//        findNavController().navigate(action)
//    }

    fun createTodo() {
        viewModel.createTodo(
            binding.writeName.text.toString(),
            navigationArgs.date
        )
        val action = TodoAdderFragmentDirections.actionTodoAdderFragmentToTodoListFragment()
        findNavController().navigate(action)
    }

//
//    private fun updateTodo() {
//        if (isEntryValid()) {
//            viewModel.updateTodo(
//                this.navigationArgs.id,
//                this.binding.writeTitle.text.toString(),
//                this.binding.writeContent.text.toString(),
//                0 //WIP
//            )
//            val action = TodoAdderFragmentDirections.actionTodoAdderFragmentToTodoListFragment(0)
//            findNavController().navigate(action)
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submitButton.setOnClickListener {createTodo()}
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoAdderBinding.inflate(inflater, container, false)
        return binding.root
    }
}


//package com.example.todomateclone.ui
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.example.todomateclone.network.RestService
//import com.example.todomateclone.network.dto.TaskDTO
//
//class TaskPagingSource(
//    private val restService: RestService
//) : PagingSource<Int, TaskDTO>() {
//
//    override fun getRefreshKey(state: PagingState<Int, TaskDTO>): Int? {
//        return null
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TaskDTO> {
//        val response = restService.getAllTasksPaged(cursor = params.key, count = params.loadSize)
//        val prevItemId = ((params.key ?: 0) - params.loadSize).let {
//            if (it > 0) it
//            else null
//        }
//        return LoadResult.Page(
//            data = response.results,
//            nextKey = response.next,
//            prevKey = response.previous
//        )
//    }
//}