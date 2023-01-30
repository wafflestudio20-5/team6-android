package com.example.todomateclone.ui.follow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todomateclone.databinding.FollowListItemBinding
import com.example.todomateclone.network.dto.FollowDTO


class FollowListAdapter(private val onItemClicked: (FollowDTO) -> Unit):
    ListAdapter<FollowDTO, FollowListAdapter.FollowViewHolder>(DiffCallback) {

    class FollowViewHolder(private val binding: FollowListItemBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(follow: FollowDTO) {
            binding.apply {
                followItem.text = follow.nickname[0].toString()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        return FollowViewHolder(
            FollowListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        val current = getItem(position)

        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }

        holder.bind(current)
    }


    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<FollowDTO>() {
            override fun areItemsTheSame(oldItem: FollowDTO, newItem: FollowDTO): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FollowDTO, newItem: FollowDTO): Boolean {
                return oldItem == newItem
            }
        }
    }

}
