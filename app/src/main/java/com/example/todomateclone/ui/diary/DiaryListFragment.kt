package com.example.todomateclone.ui.diary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todomateclone.databinding.FragmentDiaryListBinding
import com.example.todomateclone.viewmodel.DiaryViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DiaryListFragment : Fragment() {

    private var _binding: FragmentDiaryListBinding? = null
    private val binding get() = _binding!!
    private val diaryViewModel: DiaryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiaryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val floatingActionButton = binding.floatingActionButton
        val upButton = binding.upButton
        val dateText = binding.dateText
        val todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val adapter = DiaryListAdapter(
            onItemClicked = {
                val action = DiaryListFragmentDirections.actionDiaryListFragmentToDiaryDetailFragment(it.id)
                this.findNavController().navigate(action)
            }
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        dateText.text = todayDate

        // show entire diaryList
        lifecycleScope.launch {
            diaryViewModel.getDiaryList()
            diaryViewModel.diaryList.collect {
                adapter.submitList(it)
            }
        }

        upButton.setOnClickListener {
            this.findNavController().navigateUp()
        }

        floatingActionButton.setOnClickListener {
            val action = DiaryListFragmentDirections.actionDiaryListFragmentToDiaryAdderFragment(todayDate)
            this.findNavController().navigate(action)
        }
    }

}