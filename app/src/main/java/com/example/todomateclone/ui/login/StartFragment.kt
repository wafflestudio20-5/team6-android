package com.example.todomateclone.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todomateclone.MainActivity
import com.example.todomateclone.R
import com.example.todomateclone.databinding.FragmentStartBinding
import com.example.todomateclone.viewmodel.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


@Suppress("DEPRECATION")
class StartFragment : Fragment() {

    // 1. Context를 할당할 변수를 프로퍼티로 선언(어디서든 사용할 수 있게)
    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // 2. Context를 액티비티로 형변환해서 할당
        mainActivity = context as MainActivity
    }

    private val userViewModel: UserViewModel by viewModel()
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginButton = binding.loginButton
        val guestButton = binding.guestButton
        val signUpText = binding.signUpText
        val kakaoButton = binding.kakaoButton
        val googleButton = binding.googleLoginButton

        googleButton.setSize(SignInButton.SIZE_WIDE)

        loginButton.setOnClickListener {
            val action = StartFragmentDirections.actionStartFragmentToLoginFragment()
            this.findNavController().navigate(action)
        }

        guestButton.setOnClickListener {
            navigateToMain()
        }

        signUpText.setOnClickListener {
            val action = StartFragmentDirections.actionStartFragmentToSignUpFragment()
            this.findNavController().navigate(action)
        }

        kakaoButton.setOnClickListener {
            kakaoLogin()
        }

        googleButton.setOnClickListener {
            googleLogin()
        }
    }

    // 카카오 로그인
    private fun kakaoLogin() {
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        lateinit var kakaoToken: OAuthToken

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("KakaoLogin", "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i("KakaoLogin", "카카오계정으로 로그인 성공 ${token.accessToken}")

                CoroutineScope(Dispatchers.IO).launch {
                    userViewModel.kakaoLogin(token.accessToken)
                    launch(Dispatchers.Main) {
                        navigateToMain()
                    }
                }
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(mainActivity)) {
            UserApiClient.instance.loginWithKakaoTalk(mainActivity) { token, error ->
                if (error != null) {
                    Log.e("KakaoLogin", "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(mainActivity, callback = callback)
                } else if (token != null) {
                    Log.i("KakaoLogin", "카카오톡으로 로그인 성공 ${token.accessToken}")
                    kakaoToken = token
                    CoroutineScope(Dispatchers.IO).launch {
                        userViewModel.kakaoLogin(kakaoToken.accessToken)
                        launch(Dispatchers.Main) {
                            navigateToMain()
                        }
                    }
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(mainActivity, callback = callback)
        }
    }

    // 구글 로그인
    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            Log.d("GoogleLogin", "google sign in task is completed")
            handleSignInResult(task)
        }
    }

    private fun googleLogin() {
        CoroutineScope(Dispatchers.IO).launch {
            //사용자의 ID, 이메일 주소 및 기본정보를 요청하도록 로그인 구성
            //프로파일링 합니다. ID 및 기본 프로파일은 DEFAULT_SIGN_IN에 포함됩니다.
            //추가로 요청해야하는 정보는 requestScopes 를 지정하여 요청함. 꼭 필요한 것들만 요청하도록 한다.
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(Scope(Scopes.DRIVE_APPFOLDER))
                .requestServerAuthCode(getString(R.string.google_server_client_id))
                .requestIdToken(getString(R.string.google_server_client_id))
                .requestEmail()
                .build()
            val googleSignInClient = GoogleSignIn.getClient(mainActivity, gso)
            val signInIntent: Intent = googleSignInClient.signInIntent
            resultLauncher.launch(signInIntent)
            Log.d("GoogleLogin", "showing google login page")
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
            val code: String = account.serverAuthCode.toString()
            Log.d("GoogleLogin", "get idToken: ${account.idToken}")
            Log.d("GoogleLogin", "get authCode: ${account.serverAuthCode}")
            Log.d("GoogleLogin", "is Expired: ${account.isExpired}")

            // send ID Token to server and validate
            CoroutineScope(Dispatchers.IO).launch {
                userViewModel.googleLogin(code)
                Log.d("GoogleLogin", "googleLogin process is succeeded")
                launch(Dispatchers.Main) {
                    navigateToMain()
                }
            }
            // Signed in successfully, show authenticated UI.

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("GoogleLogin", "signInResult:failed code=" + e.statusCode)
        }
    }

    override fun onStart() {
        super.onStart()
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(mainActivity)
        if (account != null) {
            Log.d("GoogleLogin", "already logged in")
        } else {
            Log.d("GoogleLogin", "not yet logged in")
        }
    }

    private fun navigateToMain() {
        this.findNavController().navigate(R.id.action_global_nav_graph)
    }
}

