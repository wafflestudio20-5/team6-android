package com.example.todomateclone.ui.diary

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todomateclone.MainActivity
import com.example.todomateclone.R
import com.example.todomateclone.databinding.FragmentDiaryDetailBinding
import com.example.todomateclone.databinding.FragmentDiaryEditBinding
import com.example.todomateclone.viewmodel.DiaryViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class DiaryEditFragment : Fragment() {

    private var _binding: FragmentDiaryEditBinding? = null
    private val binding get() = _binding!!
    private val navigationArgs: DiaryDetailFragmentArgs by navArgs()
    private val diaryViewModel: DiaryViewModel by viewModel()

    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiaryEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val upButton = binding.upButton
        val saveButton = binding.saveButton
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
            // 다이얼로그를 통해 일기 저장 확인 메시지 출력
            AlertDialog.Builder(mainActivity)
                .setMessage("일기 수정사항을 저장하시겠습니까?")
                .setPositiveButton("예"
                ) { _, _ ->
                    lifecycleScope.launch {
                        diaryViewModel.updateIdDiary(
                            diaryTitle.text.toString(),
                            diaryContent.text.toString(),
                            navigationArgs.diaryId
                        )
                        findNavController().navigateUp()
                    }
                    Log.d("MyTag", "positive")
                }
                .setNegativeButton("아니오"
                ) { _, _ ->
                    findNavController().navigateUp()
                    Log.d("MyTag", "negative")
                }
                .setNeutralButton("돌아가기"
                ) { _, _ ->
                    Log.d("MyTag", "neutral")
                }
                .create()
                .show()
        }

        saveButton.setOnClickListener {
            lifecycleScope.launch {
                diaryViewModel.updateIdDiary(
                    diaryTitle.text.toString(),
                    diaryContent.text.toString(),
                    navigationArgs.diaryId
                )
                findNavController().navigateUp()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this.viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // 뒤로가기 눌렀을 때 동작할 코드
                AlertDialog.Builder(mainActivity)
                    .setMessage("수정사항을 저장하시겠습니까?")
                    .setPositiveButton("예"
                    ) { _, _ ->
                        lifecycleScope.launch {
                            diaryViewModel.updateIdDiary(
                                diaryTitle.text.toString(),
                                diaryContent.text.toString(),
                                navigationArgs.diaryId
                            )
                            findNavController().navigateUp()
                        }
                        Log.d("MyTag", "positive")
                    }
                    .setNegativeButton("아니오"
                    ) { _, _ ->
                        findNavController().navigateUp()
                        Log.d("MyTag", "negative")
                    }
                    .setNeutralButton("돌아가기"
                    ) { _, _ ->
                        Log.d("MyTag", "neutral")
                    }
                    .create()
                    .show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}