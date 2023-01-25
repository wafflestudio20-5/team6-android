package com.example.todomateclone.ui

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

class TodoListFragment : Fragment() {
    private lateinit var binding: FragmentTodoListBinding

    private val viewModel: TodoViewModel by viewModel()
    lateinit var adapter: TodoListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TodoListAdapter(
            { task -> checkFinal(task) },
            { task -> deleteFinal(task) }
        )
        binding.recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.layoutManager = layoutManager


        val todaysdate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        lateinit var yearstr: String
        lateinit var monthstr: String
        lateinit var daystr: String

        binding.apply {
            dateTextView.text = todaysdate
//            viewLifecycleOwner.lifecycleScope.launch {
//                val pager=viewModel.createPager(binding.dateTextView.text.toString())
//                pager.collect { pagingData ->
//                    adapter.submitData(pagingData)
//                }
//            }
            refreshTask()
            calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
                dateTextView.visibility = View.VISIBLE
                if(month<9) monthstr="0"+(month+1).toString()
                else monthstr=(month+1).toString()
                if(dayOfMonth<10) daystr="0"+dayOfMonth.toString()
                else daystr=dayOfMonth.toString()
                yearstr=year.toString()

                dateTextView.text = String.format("%s-%s-%s", yearstr, monthstr, daystr)

//                viewLifecycleOwner.lifecycleScope.launch {
//                    val pager=viewModel.createPager(binding.dateTextView.text.toString())
//                    pager.collect { pagingData ->
//                        adapter.submitData(pagingData)
//                    }
//                }
                refreshTask()
            }
        }

        binding.floatingActionButton.setOnClickListener {
            Log.d("TodoList to TodoAdder", binding.dateTextView.text.toString())
            val action = TodoListFragmentDirections.actionTodoListFragmentToTodoAdderFragment(binding.dateTextView.text.toString())
            this.findNavController().navigate(action)
        }



    }

    fun refreshTask() {
        viewLifecycleOwner.lifecycleScope.launch {
            val pager=viewModel.createPager(binding.dateTextView.text.toString())
            pager.collect { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    fun checkFinal(task: TaskDTO) {
        viewModel.checkTodo(task.id)
        refreshTask()
    }

    fun deleteFinal(task: TaskDTO) {
        viewModel.deleteTodo(task.id)
        refreshTask()
    }
}
