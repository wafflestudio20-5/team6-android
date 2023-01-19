package com.example.todomateclone.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todomateclone.databinding.FragmentTodoAdderBinding
import com.example.todomateclone.viewmodel.TodoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate

class TodoAdderFragment : Fragment() {
    private lateinit var binding: FragmentTodoAdderBinding

    private val viewModel: TodoViewModel by viewModel()

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
            binding.writeDate.text.toString()
        )
        Log.d("TodoAdderFragment", "createTodo happening")
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
