package com.example.todomateclone.ui.diary

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todomateclone.databinding.FragmentCommentListBinding
import com.example.todomateclone.viewmodel.CommentViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class CommentListFragment : Fragment() {

    private var _binding: FragmentCommentListBinding? = null
    private val binding get() = _binding!!
    private val navigationArgs: CommentListFragmentArgs by navArgs()
    private val commentViewModel: CommentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val upButton = binding.upButton
        val inputComment = binding.inputComment
        val adapter = CommentListAdapter(
            onItemClicked = {

            },
            onItemLongClicked = {
                true
            }
        )
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this.context)

        lifecycleScope.launch {
            commentViewModel.getCommentList(navigationArgs.diaryId)
            commentViewModel.commentList.collect {
                adapter.submitList(it)
            }
        }

        upButton.setOnClickListener {
            this.findNavController().navigateUp()
        }

        // edit text 의 키보드 다음버튼을 완료버튼으로 변경
        inputComment.imeOptions = EditorInfo.IME_ACTION_DONE
        // edit text 에 댓글 입력 후 완료버튼 클릭 시 이벤트 처리
        inputComment.setOnEditorActionListener { _, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE){
                lifecycleScope.launch {
                    commentViewModel.createComment(inputComment.text.toString(), navigationArgs.diaryId)
                }
                // Hide keyboard
                val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                        InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
                handled = true
            }
            handled
        }

    }

}