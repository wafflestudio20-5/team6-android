package com.example.todomateclone.ui.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todomateclone.R
import com.example.todomateclone.databinding.FragmentCheckFollowBinding
import com.example.todomateclone.databinding.FragmentFollowListBinding
import com.example.todomateclone.viewmodel.FollowViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckFollowFragment : Fragment() {
    private var _binding: FragmentCheckFollowBinding? = null
    private val binding get() = _binding!!
    private val followViewModel: FollowViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheckFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonFollow = binding.buttonFollow
        val buttonSearch = binding.buttonSearch
        val textviewSearchEmail = binding.textviewSearchEmail
        val textViewNickname = binding.textViewNickname

        buttonSearch.setOnClickListener() {
            val email = textviewSearchEmail.text.toString()
            // email 넣어서 계정을 찾아주는 api 실행
            // 찾아온 값으로 UI 변경
        }


        buttonFollow.setOnClickListener {
            // 한번 클릭하면 팔로우
            // 다시 클릭하면 언팔로우
            // 서버 데이터 변경 및 button 텍스트 변경
        }



    }

}
