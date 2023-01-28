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


class TodoListAdapter(private val onItemClicked: (TaskDTO) -> Unit, private val onItemClicked2: (TaskDTO) -> Unit, private val onItemClicked3: (TaskDTO) -> Unit, private val onItemClickedMenu: (TaskDTO) -> Unit) : PagingDataAdapter<TaskDTO, TodoListAdapter.TodoViewHolder>(DiffCallback) {
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
                onItemClicked(current)
            }
            holder.deletebutton.setOnClickListener {
                onItemClicked2(current)
            }
            holder.delaybutton.setOnClickListener {
                onItemClicked3(current)
            }
            holder.menubutton.setOnClickListener {
                onItemClickedMenu(current)
            }
            holder.bind(it)
        }
    }

    class TodoViewHolder(private var binding: TodoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val donebutton = binding.doneSwitch
        val deletebutton = binding.deleteButton
        val delaybutton = binding.delayButton
        val menubutton = binding.menuButton
        fun bind(todo: TaskDTO) {
            binding.apply {
                nameTextView.text = todo.name
                doneSwitch.setChecked(todo.complete)
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
