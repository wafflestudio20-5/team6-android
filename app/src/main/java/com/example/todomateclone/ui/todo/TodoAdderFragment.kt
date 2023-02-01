package com.example.todomateclone.ui.todo

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.todomateclone.databinding.FragmentTodoAdderBinding
import com.example.todomateclone.network.dto.TaskDTO
import com.example.todomateclone.viewmodel.TodoViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
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
            binding.writeStarttime.setText("13시 0분")
            binding.writeEndtime.setText("17시 0분")
        }
        else {
            binding.writeName.setText(task.name)
            binding.writeDate.setText(date)
            binding.writeStarttime.setText(task.start_time)
            binding.writeEndtime.setText(task.end_time)
        }
        binding.writeDate.setOnClickListener{datetimeClick()}
        binding.writeStarttime.setOnClickListener{starttimeClick()}
        binding.writeEndtime.setOnClickListener{endtimeClick()}

        binding.submitButton.setOnClickListener {
            if(task==null) {
                if(binding.writeName.text!=null) lifecycleScope.launch {viewModel.createTodo(binding.writeName.text.toString(), binding.writeDate.text.toString(), binding.writeStarttime.text.toString(), binding.writeEndtime.text.toString())}
            }
            else  {
                if(binding.writeName.text!=null) lifecycleScope.launch {viewModel.changeTodo(binding.writeName.text.toString(), binding.writeDate.text.toString(), binding.writeStarttime.text.toString(), binding.writeEndtime.text.toString(), task.id)}
            }
            dismiss()
            listener?.onDismiss()
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoAdderBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun datetimeClick() {
        var dates=date.split("-")
        val datePickerDialog = this.context?.let {
            DatePickerDialog(
                it,
                OnDateSetListener { datePicker, year, month, dayOfMonth ->
                    var monthstr=""
                    var daystr=""
                    var yearstr=""
                    if(month<9) monthstr="0"+(month+1).toString()
                    else monthstr=(month+1).toString()
                    if(dayOfMonth<10) daystr="0"+dayOfMonth.toString()
                    else daystr=dayOfMonth.toString()
                    yearstr=year.toString()
                    binding.writeDate.setText(yearstr + "-" + monthstr + "-" + daystr) },
                dates[0].toInt(),
                dates[1].toInt()-1,
                dates[2].toInt()
            )
        }
        datePickerDialog?.show()
    }

    fun starttimeClick() {
        var h=13
        var m=0
        if(task==null) {

        }
        else {
            val st=task.start_time.split("시 ", "분")
            h=st[0].toInt()
            m=st[1].toInt()
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
        var h=17
        var m=0
        if(task==null) {

        }
        else {
            val et=task.end_time.split("시 ", "분")
            h=et[0].toInt()
            m=et[1].toInt()
        }
        val timePickerDialog = TimePickerDialog(this.context,
            OnTimeSetListener { timePicker, i, i1 -> binding.writeEndtime.setText(i.toString() + "시 " + i1 + "분") },
            h,
            m,
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


