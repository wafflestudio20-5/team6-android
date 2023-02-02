package com.example.todomateclone.ui.user

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.todomateclone.MainActivity
import com.example.todomateclone.R
import com.example.todomateclone.databinding.FragmentUserPageBinding
import com.example.todomateclone.util.AuthStorage
import com.example.todomateclone.viewmodel.UserDetailViewModel
import com.example.todomateclone.viewmodel.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.system.exitProcess

class UserPageFragment : Fragment() {

    private val userDetailViewModel: UserDetailViewModel by viewModel()
    private val userViewModel: UserViewModel by viewModel()
    private var _binding: FragmentUserPageBinding? = null
    private val binding get() = _binding!!

    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // 2. Context를 액티비티로 형변환해서 할당
        mainActivity = context as MainActivity
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserPageBinding.inflate(inflater, container, false)

        CoroutineScope(Dispatchers.IO).launch {

            lifecycleScope.launchWhenStarted {
                userDetailViewModel.user.collectLatest {
                    userDetailViewModel.getUser()
                    binding.textViewEmail.text = userDetailViewModel.user.value?.email.toString()
                }
            }
        }
            return binding.root
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileButton = binding.buttonProfile
        val changePasswordButton = binding.buttonChangePassword
        val deleteAccountButton = binding.buttonDeleteAccount
        val googleRevokeAccessButton = binding.buttonGoogleRevokeAccess


        profileButton.setOnClickListener {
            val action = UserPageFragmentDirections.actionUserPageFragmentToProfileFragment()
            this.findNavController().navigate(action)
        }

        changePasswordButton.setOnClickListener() {
            val email = userDetailViewModel.user.value?.email.toString()
            CoroutineScope(Dispatchers.IO).launch {
                userDetailViewModel.sendResetEmail(email)
            }
            val action = UserPageFragmentDirections.actionUserPageFragmentToChangePasswordFragment()
            this.findNavController().navigate(action)
        }

        deleteAccountButton.setOnClickListener() {
            CoroutineScope(Dispatchers.IO).launch {
                userViewModel.deleteUser()
                userViewModel.logout()
            }
            this.findNavController().navigate(R.id.action_global_login_graph)
        }

        // 구글 계정 연동 해제
        googleRevokeAccessButton.setOnClickListener {
            AlertDialog.Builder(mainActivity)
                .setTitle("구글 계정 연동 해제")
                .setMessage("정말로 계정 연동을 해제하시겠습니까?\n연동 해제 시 등록된 사용자 정보가 삭제되며 회원가입을 다시 진행해야합니다.\n앱은 자동 종료됩니다.")
                .setPositiveButton("예"
                ) { _, _ ->
                    Log.d("MyTag", "positive")
                    googleRevokeAccess()
                }
                .setNegativeButton("아니오"
                ) { _, _ -> Log.d("MyTag", "negative") }
                .setNeutralButton("돌아가기"
                ) { _, _ -> Log.d("MyTag", "neutral") }
                .create()
                .show()
        }
    }

    private fun googleRevokeAccess() {
        lifecycleScope.launch {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_server_client_id))
                .requestEmail()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(mainActivity, gso)

            googleSignInClient.revokeAccess()
                .addOnCompleteListener(mainActivity) {
                    Log.d("GoogleRevoke", "Revoked")
                }

            // 회원 탈퇴 진행
            userViewModel.deleteUser()
            // 앱 내부에 저장된 유저 정보 삭제 진행
            userViewModel.logout()
            Log.d("GoogleRevoke", "회원 탈퇴 및 로그아웃이 완료되었습니다.")
            // 앱을 종료함
            mainActivity.finishAffinity()
            System.runFinalization()
            exitProcess(0)
        }
            this.findNavController().navigate(R.id.action_global_login_graph)
        }
    }


}