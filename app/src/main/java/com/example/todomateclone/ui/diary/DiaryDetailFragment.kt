package com.example.todomateclone.ui.diary

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todomateclone.MainActivity
import com.example.todomateclone.R
import com.example.todomateclone.databinding.FragmentDiaryDetailBinding
import com.example.todomateclone.viewmodel.DiaryViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class DiaryDetailFragment : Fragment() {

    private var _binding: FragmentDiaryDetailBinding? = null
    private val binding get() = _binding!!
    private val navigationArgs: DiaryDetailFragmentArgs by navArgs()
    private val diaryViewModel: DiaryViewModel by viewModel()

    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiaryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val upButton = binding.upButton
        val diaryTitle = binding.diaryTitle
        val diaryContent = binding.diaryContent
        val commentText = binding.commentText
        val toolbar = binding.toolbar

        lifecycleScope.launch {
            diaryViewModel.getIdDiary(navigationArgs.diaryId)
            diaryViewModel.diary.collect {
                diaryTitle.text = it?.title
                diaryContent.text = it?.context
            }
        }

        // ????????? ???????????? ?????? ?????? ??? ?????? ??????
        if (navigationArgs.searchDid == -1) {
            // toolbar menu selected action
            toolbar.inflateMenu(R.menu.diary_menu)
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.edit_diary -> {
                        val action = DiaryDetailFragmentDirections.actionDiaryDetailFragmentToDiaryEditFragment(navigationArgs.diaryId)
                        this.findNavController().navigate(action)
                        true
                    }
                    R.id.delete_diary -> {
                        AlertDialog.Builder(mainActivity)
                            .setMessage("????????? ?????????????????????????")
                            .setPositiveButton("???"
                            ) { _, _ ->
                                lifecycleScope.launch {
                                    diaryViewModel.deleteIdDiary(
                                        navigationArgs.diaryId
                                    )
                                    findNavController().navigateUp()
                                }
                                Log.d("MyTag", "positive")
                            }
                            .setNegativeButton("?????????"
                            ) { _, _ ->
                                Log.d("MyTag", "negative")
                            }
                            .create()
                            .show()
                        true
                    }
                    else -> true
                }
            }
        }

        upButton.setOnClickListener {
            this.findNavController().navigateUp()
        }

        commentText.setOnClickListener {
            val action = DiaryDetailFragmentDirections.actionDiaryDetailFragmentToCommentListFragment(navigationArgs.diaryId)
            this.findNavController().navigate(action)
        }
    }
}

