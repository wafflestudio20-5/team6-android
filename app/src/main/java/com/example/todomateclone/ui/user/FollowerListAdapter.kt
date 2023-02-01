package com.example.todomateclone.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View.INVISIBLE
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todomateclone.databinding.FollowListItemBinding
import com.example.todomateclone.databinding.TodoListItemBinding
import com.example.todomateclone.network.dto.FolloweeDTO
import com.example.todomateclone.network.dto.FollowerDTO
import com.example.todomateclone.network.dto.TaskDTO


class FollowerListAdapter() : PagingDataAdapter<FollowerDTO, FollowerListAdapter.FollowerViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        return FollowerViewHolder(
            FollowListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        val current = getItem(position)
        current?.let {
            holder.bind(it)
        }
    }



    class FollowerViewHolder(private var binding: FollowListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val visitButton = binding.visitButton
        fun bind(follower: FollowerDTO) {
            binding.apply {
                visitButton.visibility = INVISIBLE
                emailTextView.setText(follower.from_user_email)
                nicknameTextView.setText("닉네임: "+follower.from_user_nickname)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<FollowerDTO>() {
            override fun areItemsTheSame(oldItem: FollowerDTO, newItem: FollowerDTO): Boolean {
                return oldItem.from_user_id == newItem.from_user_id
            }

            override fun areContentsTheSame(oldItem: FollowerDTO, newItem: FollowerDTO): Boolean {
                return oldItem.from_user_email == newItem.from_user_email
            }
        }
    }
}
