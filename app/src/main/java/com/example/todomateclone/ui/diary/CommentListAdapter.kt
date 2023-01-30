package com.example.todomateclone.ui.diary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todomateclone.databinding.CommentListItemBinding
import com.example.todomateclone.network.dto.CommentDTO

class CommentListAdapter(
    private val onItemClicked: (CommentDTO) -> Unit,
    private val onItemLongClicked: (CommentDTO) -> Boolean
) :
    ListAdapter<CommentDTO, CommentListAdapter.CommentViewHolder>(DiffCallback) {

    class CommentViewHolder(private val binding: CommentListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(comment: CommentDTO) {
            binding.apply {
                commentContent.text = comment.context
                date.text = comment.created_at
                user.text = comment.nickname
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            CommentListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val current = getItem(position)

        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }

        holder.itemView.setOnLongClickListener {
            onItemLongClicked(current)
        }

        holder.bind(current)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<CommentDTO>() {
            override fun areItemsTheSame(oldItem: CommentDTO, newItem: CommentDTO): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CommentDTO, newItem: CommentDTO): Boolean {
                return oldItem == newItem
            }
        }
    }
}