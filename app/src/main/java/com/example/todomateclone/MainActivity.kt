package com.example.todomateclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.todomateclone.databinding.ActivityMainBinding
import com.example.todomateclone.util.AuthStorage
import com.kakao.sdk.common.util.Utility
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val authStorage: AuthStorage by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("KAKAO", "keyhash : ${Utility.getKeyHash(this)}")

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val navGraph = navHostFragment.navController.navInflater.inflate(R.navigation.nav_graph)

        // auto login
        if (authStorage.tokenValid == "") {
            navGraph.setStartDestination(R.id.startFragment)
            navController.graph = navGraph
        } else {
            navGraph.setStartDestination(R.id.mainFragment)
            navController.graph = navGraph
        }
    }
}