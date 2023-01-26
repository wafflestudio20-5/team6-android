package com.example.todomateclone.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.todomateclone.R
import com.example.todomateclone.databinding.FragmentTodoAdderBinding
import com.example.todomateclone.viewmodel.TodoViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Job
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import androidx.lifecycle.viewModelScope


class TodoAdderFragment(val date: String) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentTodoAdderBinding

    private val viewModel: TodoViewModel by viewModel()
//    private val navigationArgs: TodoAdderFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submitButton.setOnClickListener {
            viewModel.createTodo(binding.writeName.text.toString(), date)
            dismiss()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoAdderBinding.inflate(inflater, container, false)
        return binding.root
    }
}


