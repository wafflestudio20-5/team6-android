package com.example.todomateclone.ui.todo

import android.content.ClipData
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
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
import com.example.todomateclone.databinding.FragmentTodoFixerBinding
import com.example.todomateclone.network.dto.TaskDTO
import com.example.todomateclone.ui.TodoListAdapter


class TodoFixerFragment(val task: TaskDTO) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentTodoFixerBinding

    private var listener: OnDismissListener? = null

    fun setOnDismissListener(listener: OnDismissListener) {
        this.listener = listener
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fixButton.setOnClickListener {
            dismiss()
            listener?.onDismissFix(task)
        }
        binding.deleteButton.setOnClickListener{
            dismiss()
            listener?.onDismissDelete(task)
        }
        binding.delayButton.setOnClickListener{
            dismiss()
            listener?.onDismissDelay(task)
        }
        binding.todayButton.setOnClickListener {
            dismiss()
            listener?.onDismissToday(task)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoFixerBinding.inflate(inflater, container, false)
        return binding.root
    }
}


interface OnDismissListener {
    fun onDismissFix(task: TaskDTO)
    fun onDismissDelete(task: TaskDTO)
    fun onDismissDelay(task: TaskDTO)
    fun onDismissToday(task: TaskDTO)


}