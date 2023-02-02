package com.example.todomateclone.ui

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import com.example.todomateclone.MainActivity
import com.example.todomateclone.R
import com.example.todomateclone.databinding.FragmentMainBinding
import com.example.todomateclone.ui.user.UserPageFragmentDirections
import com.example.todomateclone.util.AuthStorage
import com.example.todomateclone.viewmodel.UserViewModel
import com.google.android.material.navigation.NavigationView
import com.kakao.auth.StringSet.access_token
import com.kakao.auth.StringSet.refresh_token
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModel()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val authStorage: AuthStorage by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 최초 앱 실행 시 로그인 화면(startFragment)으로 이동
        lifecycleScope.launch {
            authStorage.authInfo.collect {
                if (it == null) {
                    Log.d("MainFragment", "navigate to login graph")
                    findNavController().navigate(R.id.action_global_login_graph)
                }
            }
        }

        //refresh_token 만료 시 재로그인 요청
        lifecycleScope.launchWhenStarted {
            Log.d("MainFragment", "Check refreshToken")
            userViewModel.verifyToken(authStorage.authInfo.value!!.refreshToken)
            userViewModel.refreshTokenValid.collect {
                if (it != true) {
                    userViewModel.logout()
                    findNavController().navigate(R.id.action_global_login_graph)
                }
            }
        }

        val logoutButton = binding.logoutButton
        val toolbarTitle = binding.toolbarTitle
        val followListButton = binding.followListButton
        val toolbar: Toolbar = binding.toolbar
        val navigationView: NavigationView = binding.navigationView
        val drawerLayout: DrawerLayout = binding.drawerLayout

        toolbar.inflateMenu(R.menu.appbar) // 여기서 appbar layout 확장
        navigationView.itemIconTintList = null

        // 툴바 타이틀 글꼴 기울이기, 굵게
        toolbarTitle.setTypeface(null, Typeface.BOLD_ITALIC)

        // when click button, user logout
        logoutButton.setOnClickListener {
            userViewModel.logout()
            // navigate to start fragment
            this.findNavController().navigate(R.id.action_global_login_graph)
        }

        followListButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToFollowListFragment()
            this.findNavController().navigate(action)
        }

        // toolbar menu selected action
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_icon -> {
                    drawerLayout.openDrawer(GravityCompat.END)
                    Log.d("MainFragment", "drawer is opened")
                    true
                }
                R.id.notice_icon -> {
                    true
                }
                R.id.follow_icon -> {
                    val action = MainFragmentDirections.actionMainFragmentToSearchUserFragment()
                    this.findNavController().navigate(action)
                    true
                }
                else -> true
            }
        }
        // navigationView item selected action
        navigationView.setNavigationItemSelectedListener { menuItem ->
            val id = menuItem.itemId
            //it's possible to do more actions on several items, if there is a large amount of items I prefer switch(){case} instead of if()
            when (menuItem.itemId) {
                // navigate to user page
                R.id.nav_user_page -> {
                    val action = MainFragmentDirections.actionMainFragmentToUserPageFragment()
                    this.findNavController().navigate(action)
                }
                R.id.nav_todo_page -> {
                    val action = MainFragmentDirections.actionMainFragmentToTodoListFragment()
                    this.findNavController().navigate(action)
                }
                R.id.nav_diary_page -> {
                    val action = MainFragmentDirections.actionMainFragmentToDiaryListFragment()
                    this.findNavController().navigate(action)
                }
            }
            //This is for maintaining the behavior of the Navigation view
            onNavDestinationSelected(menuItem, this.findNavController())
            //This is for closing the drawer after acting on it
            drawerLayout.closeDrawer(GravityCompat.END)
            true
        }
    }
}