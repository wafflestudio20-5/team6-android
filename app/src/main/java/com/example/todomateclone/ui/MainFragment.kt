package com.example.todomateclone.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
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
        val toolbar: Toolbar = binding.toolbar
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
            if (childFragmentManager.backStackEntryCount != 1) {
                parentFragmentManager.popBackStack(
                    "MainFragment",
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
            }
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

        navigationView.setNavigationItemSelectedListener { menuItem ->
            val id = menuItem.itemId
            //it's possible to do more actions on several items, if there is a large amount of items I prefer switch(){case} instead of if()
            when (id) {
                // navigate to user page
                R.id.nav_user_page -> {
                    Log.d("MainFragment", "navigate to user page")
                }
                R.id.nav_todo_page -> {}
                R.id.nav_diary_page -> {}
            }
            //This is for maintaining the behavior of the Navigation view
            onNavDestinationSelected(menuItem, this.findNavController() )
            //This is for closing the drawer after acting on it
            drawerLayout.closeDrawer(GravityCompat.END)
            true
        }
    }
}