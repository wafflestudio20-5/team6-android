package com.example.todomateclone.ui

import android.app.ProgressDialog.show
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todomateclone.databinding.FragmentTodoListBinding
import com.example.todomateclone.network.dto.TaskDTO
import com.example.todomateclone.viewmodel.TodoViewModel
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import android.app.ProgressDialog.show
import com.example.todomateclone.databinding.FragmentDiaryJcyBinding

class DiaryJCYFragment : Fragment() {
    private lateinit var binding: FragmentDiaryJcyBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiaryJcyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.goTodo.setOnClickListener{
            val action = DiaryJCYFragmentDirections.actionDiaryjcyFragmentToTodoListFragment()
            this.findNavController().navigate(action)
        }

    }

}
