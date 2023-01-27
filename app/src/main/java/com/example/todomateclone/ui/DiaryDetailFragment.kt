package com.example.todomateclone.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todomateclone.databinding.FragmentDiaryDetailBinding
import com.example.todomateclone.viewmodel.DiaryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class DiaryDetailFragment : Fragment() {

    private var _binding: FragmentDiaryDetailBinding? = null
    private val binding get() = _binding!!
    private val navigationArgs: DiaryDetailFragmentArgs by navArgs()
    private val diaryViewModel: DiaryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiaryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val upButton = binding.upButton
        val saveButton = binding.saveButton
        val clearButton = binding.clearButton
        val diaryTitle = binding.diaryTitle
        val diaryContent = binding.diaryContent

        lifecycleScope.launch {
            diaryViewModel.getIdDiary(navigationArgs.diaryId)
            diaryViewModel.diary.collect {
                diaryTitle.setText(it?.title)
                diaryContent.setText(it?.context)
            }
        }

        upButton.setOnClickListener {
            this.findNavController().navigateUp()
        }

        saveButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                diaryViewModel.updateIdDiary(
                    diaryTitle.text.toString(),
                    diaryContent.text.toString(),
                    navigationArgs.diaryId
                )
            }
            this.findNavController().navigateUp()
        }

        clearButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                diaryViewModel.deleteIdDiary(
                    navigationArgs.diaryId
                )
            }
            this.findNavController().navigateUp()
        }
    }


}