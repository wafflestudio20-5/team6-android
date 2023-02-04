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
import com.example.todomateclone.MainActivity
import com.example.todomateclone.databinding.FragmentCommentListBinding
import com.example.todomateclone.viewmodel.CommentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class CommentListFragment : Fragment() {

    private var _binding: FragmentCommentListBinding? = null
    private val binding get() = _binding!!
    private val navigationArgs: CommentListFragmentArgs by navArgs()
    private val commentViewModel: CommentViewModel by viewModel()

    // 1. Context를 할당할 변수를 프로퍼티로 선언(어디서든 사용할 수 있게)
    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // 2. Context를 액티비티로 형변환해서 할당
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // view in UI
        val upButton = binding.upButton
        val inputComment = binding.inputComment

        val adapter = CommentListAdapter(
            onItemClicked = {

            },
            onItemLongClicked = {
                val bottomSheet = CommentBottomSheetFragment(it.id, navigationArgs.diaryId)
                bottomSheet.show(childFragmentManager, bottomSheet.tag)
                true
            }
        )
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this.context)

        // commentList 를 불러와서 보여줌
        CoroutineScope(Dispatchers.Main).launch {
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
                // clear edit text
                inputComment.text.clear()
                // Hide keyboard
                val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                        InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
                handled = true
            }
            handled
        }

        // 다른 fragment 로부터 결과를 받아와 실행
        childFragmentManager.setFragmentResultListener("requestKey", this.viewLifecycleOwner) { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
            val result = bundle.getBoolean("bundleKey")
            // Do something with the result
            if (result) {
                lifecycleScope.launch {
                    commentViewModel.getCommentList(navigationArgs.diaryId)
                }
            }
        }
    }
}