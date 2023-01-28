package com.example.todomateclone.ui.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todomateclone.databinding.FragmentTodoListBinding
import com.example.todomateclone.network.dto.TaskDTO
import com.example.todomateclone.ui.todo.TodoFixerFragment
import com.example.todomateclone.ui.TodoListAdapter
import com.example.todomateclone.viewmodel.TodoViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TodoListFragment : Fragment(), OnDismissListener, OnDismissListenerAdder {
    private lateinit var binding: FragmentTodoListBinding

    private val viewModel: TodoViewModel by viewModel()
    lateinit var adapter: TodoListAdapter

    override fun onDismiss() {
        refreshTask()
    }

    override fun onDismissFix(task: TaskDTO) {
        val bottomSheetDialog = TodoAdderFragment(binding.dateTextView.text.toString(), task)
        bottomSheetDialog.setOnDismissListenerAdder(this)
        bottomSheetDialog.show(requireFragmentManager(), "BottomSheetDialog")
        refreshTask()
    }

    override fun onDismissDelay(task: TaskDTO) {
        viewModel.delayTodo(task.id)
        refreshTask()
    }

    override fun onDismissDelete(task: TaskDTO) {
        viewModel.deleteTodo(task.id)
        refreshTask()
    }


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
            { task -> callTodoFixer(task)}

        )
        binding.recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)


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

                refreshTask()
            }
        }

        binding.floatingActionButton.setOnClickListener {
            val bottomSheetDialog = TodoAdderFragment(binding.dateTextView.text.toString(), null)
            bottomSheetDialog.setOnDismissListenerAdder(this)
            bottomSheetDialog.show(requireFragmentManager(), "BottomSheetDialog")
        }

        binding.refreshButton.setOnClickListener { refreshTask() }
        binding.goDiary.setOnClickListener{
            val action = TodoListFragmentDirections.actionTodoListFragmentToDiaryjcyFragment()
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
//
    fun callTodoFixer(task: TaskDTO) {
        val bottomSheetDialog = TodoFixerFragment(task)
        bottomSheetDialog.setOnDismissListener(this)
        bottomSheetDialog.show(requireFragmentManager(), "BottomSheetDialog")
    }

}
