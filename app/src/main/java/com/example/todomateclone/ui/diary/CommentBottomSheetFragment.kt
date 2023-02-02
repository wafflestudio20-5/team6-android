package com.example.todomateclone.ui.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import com.example.todomateclone.R
import com.example.todomateclone.viewmodel.CommentViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommentBottomSheetFragment(private val commentId: Int, private val diaryId: Int): BottomSheetDialogFragment() {

    private val commentViewModel: CommentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.comment_bottom_sheet_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.edit_comment).setOnClickListener {
            dismiss()
        }

        view.findViewById<TextView>(R.id.delete_comment).setOnClickListener {
            lifecycleScope.launch{
                commentViewModel.deleteIdComment(cid = commentId, did = diaryId)
                val result = true
                // Use the Kotlin extension in the fragment-ktx artifact
                setFragmentResult("requestKey", bundleOf("bundleKey" to result))
                dismiss()
            }
        }

    }
}