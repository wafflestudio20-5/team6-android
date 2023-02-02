package com.example.todomateclone.ui.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todomateclone.databinding.FragmentDiaryCalendarBinding
import com.example.todomateclone.viewmodel.DiaryViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DiaryCalendarFragment : Fragment() {

    private lateinit var binding: FragmentDiaryCalendarBinding
    private val navArgs: DiaryCalendarFragmentArgs by navArgs()
    private val diaryViewModel: DiaryViewModel by viewModel()
    lateinit var adapter: DiaryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiaryCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        lateinit var yearstr: String
        lateinit var monthstr: String
        lateinit var daystr: String


        adapter = DiaryListAdapter(
            onItemClicked = {
                val action = DiaryCalendarFragmentDirections.actionDiaryCalendarFragmentToDiaryDetailFragment(it.id)
                this.findNavController().navigate(action)
            }
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        binding.apply {
            if(navArgs.searchedId>0) {
                floatingActionButton.visibility = View.INVISIBLE
            }

            dateTextView.text = todayDate

            when (navArgs.searchedId > 0) {
                true -> viewLifecycleOwner.lifecycleScope.launch {
                    diaryViewModel.getSearchedDateDiary(navArgs.searchedId, binding.dateTextView.text.toString())
                    diaryViewModel.searchedDateDiary.collect {
                        adapter.submitList(listOf(it))
                    }
                }

                else -> viewLifecycleOwner.lifecycleScope.launch {
                    diaryViewModel.getDateDiaryList(binding.dateTextView.text.toString())
                    diaryViewModel.dateDiaryList.collect {
                        adapter.submitList(it)
                    }
                }
            }

            calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
                dateTextView.visibility = View.VISIBLE
                monthstr = if(month<9) "0"+(month+1).toString()
                else (month+1).toString()
                daystr = if(dayOfMonth<10) "0$dayOfMonth"
                else dayOfMonth.toString()
                yearstr=year.toString()

                dateTextView.text = String.format("%s-%s-%s", yearstr, monthstr, daystr)

                when (navArgs.searchedId > 0) {
                    true -> viewLifecycleOwner.lifecycleScope.launch {
                        diaryViewModel.getSearchedDateDiary(navArgs.searchedId, binding.dateTextView.text.toString())
                        diaryViewModel.searchedDateDiary.collect {
                            adapter.submitList(listOf(it))
                        }
                    }

                    else -> viewLifecycleOwner.lifecycleScope.launch {
                        diaryViewModel.getDateDiaryList(binding.dateTextView.text.toString())
                        diaryViewModel.dateDiaryList.collect {
                            adapter.submitList(it)
                        }
                    }
                }
            }
        }

        binding.goTodo.setOnClickListener {
            this.findNavController().navigateUp()
        }

        binding.floatingActionButton.setOnClickListener {
            val action = DiaryCalendarFragmentDirections.actionDiaryCalendarFragmentToDiaryAdderFragment(binding.dateTextView.text.toString())
            this.findNavController().navigate(action)
        }
    }
}
