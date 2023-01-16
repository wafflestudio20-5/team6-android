package com.example.todomateclone.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.todomateclone.R
import com.example.todomateclone.databinding.FragmentMainBinding
import com.example.todomateclone.viewmodel.UserViewModel
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
        val menuHost: MenuHost = requireActivity()

        toolbar.inflateMenu(R.menu.appbar)

        // when click button, user logout
        logoutButton.setOnClickListener {
            userViewModel.logout()
            // navigate to start fragment
            val action = MainFragmentDirections.actionMainFragmentToStartFragment()
            this.findNavController().navigate(action)
            // fragment backstack clear
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

                val menuButton = menu.findItem(R.id.menu_icon)
                val noticeButton = menu.findItem(R.id.notice_icon)
                val followButton = menu.findItem(R.id.follow_icon)
                menuButton.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_outline_menu_24 )
                noticeButton.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_baseline_favorite_border_24 )
                followButton.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_baseline_search_24 )
                menuInflater.inflate(R.menu.appbar, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.menu_icon -> {}
                    R.id.notice_icon -> {}
                    R.id.follow_icon -> {}
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}