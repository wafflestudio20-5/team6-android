package com.example.todomateclone.ui.todo

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todomateclone.databinding.FragmentTodoAdderBinding
import com.example.todomateclone.network.dto.TaskDTO
import com.example.todomateclone.viewmodel.TodoViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class TodoAdderFragment(val date: String, val task: TaskDTO?) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentTodoAdderBinding

    private var listener: OnDismissListenerAdder? = null


    private val viewModel: TodoViewModel by viewModel()
//    private val navigationArgs: TodoAdderFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(task==null) {
            binding.writeDate.setText(date)
        }
        else {
            binding.writeName.setText(task.name)
            binding.writeDate.setText(date)
//            binding.writeStarttime.setText(task.start_time)
//            binding.writeEndtime.setText(task.end_time)
        }
        binding.submitButton.setOnClickListener {
            if(task==null) {
                viewModel.createTodo(binding.writeName.text.toString(), date)
            }
            else  {
                viewModel.changeTodo(binding.writeName.text.toString(), task.id)
            }
            dismiss()
            listener?.onDismiss()
        }
        binding.writeDate.setOnClickListener{datetimeClick()}
        binding.writeStarttime.setOnClickListener{starttimeClick()}
        binding.writeEndtime.setOnClickListener{endtimeClick()}
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoAdderBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun datetimeClick() {
        val dates=date.split("-")
        val datePickerDialog = this.context?.let {
            DatePickerDialog(
                it,
                OnDateSetListener { datePicker, i, i1, i2 -> binding.writeDate.setText(i.toString() + "-" + i1 + "-" + i2) },
                dates[0].toInt(),
                dates[1].toInt(),
                dates[2].toInt()
            )
        }
        datePickerDialog?.show()
    }

    fun starttimeClick() {
        val h=13
        val m=0
        if(task==null) {

        }
        else {
            //h=task.starttime...
        }
        val timePickerDialog = TimePickerDialog(this.context,
            OnTimeSetListener { timePicker, i, i1 -> binding.writeStarttime.setText(i.toString() + "시 " + i1 + "분") },
            h,
            m,
            true
        )
        timePickerDialog.show()
    }

    fun endtimeClick() {
        val h=17
        val m=0
        if(task==null) {

        }
        else {
            //h=task.endtime...
        }
        val timePickerDialog = TimePickerDialog(this.context,
            OnTimeSetListener { timePicker, i, i1 -> binding.writeEndtime.setText(i.toString() + "시 " + i1 + "분") },
            0,
            0,
            true
        )
        timePickerDialog.show()
    }

    fun setOnDismissListenerAdder(listener: OnDismissListenerAdder) {
        this.listener = listener
    }

}

interface OnDismissListenerAdder {
    fun onDismiss()

}


