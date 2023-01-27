package com.example.todomateclone.ui.diary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todomateclone.databinding.DiaryListItemBinding
import com.example.todomateclone.network.dto.DiaryDTO

class DiaryListAdapter(private val onItemClicked: (DiaryDTO) -> Unit):
    ListAdapter<DiaryDTO, DiaryListAdapter.DiaryViewHolder>(DiffCallback) {

    class DiaryViewHolder(private val binding: DiaryListItemBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(diary: DiaryDTO) {
            binding.apply {
                title.text = diary.title
                date.text = diary.date
                user.text = diary.nickname
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        return DiaryViewHolder(
            DiaryListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        val current = getItem(position)

        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }

        holder.bind(current)
    }


    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<DiaryDTO>() {
            override fun areItemsTheSame(oldItem: DiaryDTO, newItem: DiaryDTO): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DiaryDTO, newItem: DiaryDTO): Boolean {
                return oldItem == newItem
            }
        }
    }

}