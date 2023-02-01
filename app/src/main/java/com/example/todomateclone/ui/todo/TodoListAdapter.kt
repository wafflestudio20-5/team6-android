package com.example.todomateclone.ui

import android.annotation.SuppressLint
import android.content.ClipData
import android.util.Log
import android.view.LayoutInflater
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todomateclone.R
import com.example.todomateclone.databinding.TodoListItemBinding
import com.example.todomateclone.network.dto.TaskDTO
import com.example.todomateclone.ui.todo.TodoFixerFragment
import java.text.SimpleDateFormat


class TodoListAdapter(private val onItemClicked: (TaskDTO) -> Unit, private val onItemClickedMenu: (TaskDTO) -> Unit, private val isSearched: Boolean) : PagingDataAdapter<TaskDTO, TodoListAdapter.TodoViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            TodoListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val current = getItem(position)
        current?.let {
            holder.donebutton.setOnClickListener {
                onItemClicked(current)
            }
            holder.menubutton.setOnClickListener {
                onItemClickedMenu(current)
            }

            if(isSearched) {
                holder.menubutton.visibility = INVISIBLE
                holder.donebutton.setClickable(false);
                holder.donebutton.setFocusable(false);
            } //다른 사람의 것은 수정이나 완료체크 불가
            holder.bind(it)
            Log.d("helleeeeeeo", "binding followeeviewholder")
        }
    }



    class TodoViewHolder(private var binding: TodoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val donebutton = binding.doneSwitch
        val menubutton = binding.menuButton
        fun bind(todo: TaskDTO) {
            binding.apply {
                nameTextView.text = todo.name
                doneSwitch.setChecked(todo.complete)
                starttimeTextView.text = todo.start_time
                endtimeTextView.text = todo.end_time
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
