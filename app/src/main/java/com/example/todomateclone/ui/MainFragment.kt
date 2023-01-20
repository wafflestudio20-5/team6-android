package com.example.todomateclone.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.todomateclone.R
import com.example.todomateclone.databinding.FragmentMainBinding
import com.example.todomateclone.viewmodel.UserViewModel
import com.google.android.material.navigation.NavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModel()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val logoutButton = binding.logoutButton
        val toolbar:Toolbar = binding.toolbar
        val navigationView: NavigationView = binding.navigationView
        val drawerLayout: DrawerLayout = binding.drawerLayout

        toolbar.inflateMenu(R.menu.appbar) // 여기서 appbar layout 확장
        navigationView.itemIconTintList = null

        // when click button, user logout
        logoutButton.setOnClickListener {
            userViewModel.logout()
            // navigate to start fragment
            val action = MainFragmentDirections.actionMainFragmentToStartFragment()
            this.findNavController().navigate(action)
        }

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
                    true
                }
                else -> true
            }
        }

        binding.todoListButton.setOnClickListener {
            navigateToTodo()
        }
    }

    private fun navigateToTodo() {
        val action = MainFragmentDirections.actionMainFragmentToTodoListFragment()
        this.findNavController().navigate(action)
    }
}