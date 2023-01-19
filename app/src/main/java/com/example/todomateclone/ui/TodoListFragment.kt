package com.example.todomateclone.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todomateclone.databinding.FragmentTodoListBinding
import com.example.todomateclone.viewmodel.TodoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TodoListFragment : Fragment() {
    private lateinit var binding: FragmentTodoListBinding

    private val viewModel: TodoViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = TodoListAdapter {
            //viewModel.toggleTodo(it.id, it.title, it.content, it.done)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
//        binding.goDiary.setOnClickListener {
//            val action = TodoListFragmentDirections.actionTodoListFragmentToItemListFragment()
//            this.findNavController().navigate(action)
//        }
//        viewModel.allTodos.observe(this.viewLifecycleOwner) { todos ->
//            todos.let {
//                Log.d("TodoListFragment","size is ${it.size}")
//                adapter.submitList(it)
//            }
//        }
        //Todo 부분 완료시 추후 DiaryFragment 로 navigate하도록 추가 예정

        binding.floatingActionButton.setOnClickListener {
            val action = TodoListFragmentDirections.actionTodoListFragmentToTodoAdderFragment()
            this.findNavController().navigate(action)
        }
    }
}
