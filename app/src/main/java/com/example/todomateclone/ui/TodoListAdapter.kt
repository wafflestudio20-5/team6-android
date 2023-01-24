package com.example.todomateclone.ui

import android.annotation.SuppressLint
import android.content.ClipData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todomateclone.R
import com.example.todomateclone.databinding.TodoListItemBinding
import com.example.todomateclone.network.dto.TaskDTO
import java.text.SimpleDateFormat


class TodoListAdapter(private val onItemClicked1: (TaskDTO) -> Unit) : PagingDataAdapter<TaskDTO, TodoListAdapter.TodoViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            TodoListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val current = getItem(position)
        current?.let {
            holder.donebutton.setOnClickListener {
                onItemClicked1(current)
            }
            holder.bind(it)
        }
    }

    class TodoViewHolder(private var binding: TodoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val donebutton = binding.doneSwitch

        fun bind(todo: TaskDTO) {
            binding.apply {
                nameTextView.text = todo.name
//                if(todo.done>0) doneSwitch.setChecked(true)
//                else doneSwitch.setChecked(false)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<TaskDTO>() {
            override fun areItemsTheSame(oldItem: TaskDTO, newItem: TaskDTO): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TaskDTO, newItem: TaskDTO): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}
