package com.ray.monsterhunter


import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ray.monsterhunter.databinding.ActivityMainBinding
import com.ray.monsterhunter.ext.getVmFactory
import com.ray.monsterhunter.util.CurrentFragmentType
import com.ray.monsterhunter.util.UserManager


class MainActivity : BaseActivity() {
    val viewModel by viewModels<MainViewModel> { getVmFactory() }
    private lateinit var binding: ActivityMainBinding
    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.navigation_chatroom -> {

                    findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_chatRoomFragment)
                    getToolbar()
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_friend -> {

                    findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_friendFragment)
                    getToolbar()
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_home -> {

                    findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_homeFragment)
                    getToolbar()
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_history -> {

                    findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_historyFragment)
                    getToolbar()
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_profile -> {

                    findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_profileFragment)
                    hidingToolbar()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    private val drawerNavigationItemSelectedListener =
        NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.navigation_chatroom -> {

                    findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_chatRoomFragment)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_friend -> {

                    findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_friendFragment)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_home -> {

                    findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_homeFragment)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_history -> {

                    findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_historyFragment)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_profile -> {

                    findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_profileFragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = FirebaseFirestore.getInstance()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.bottomNav.selectedItemId = R.id.navigation_home
        binding.bottomNav.itemBackground = null

        binding.bottomNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        binding.drawerNavView.setNavigationItemSelectedListener(drawerNavigationItemSelectedListener)

        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = bars.top)
            insets
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.bottomNav) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(bottom = bars.bottom)
            insets
        }


        val navigationView = findViewById<View>(R.id.drawerNavView) as NavigationView
        val headerLayout = navigationView.inflateHeaderView(R.layout.item_drawer_heater)
        val ivHeaderPhoto: ImageView = headerLayout.findViewById(R.id.profile_image)
        val userName: TextView = headerLayout.findViewById(R.id.profile_userId)

        UserManager.userData.id = FirebaseAuth.getInstance().currentUser?.displayName
        UserManager.userData.email = FirebaseAuth.getInstance().currentUser?.email
        UserManager.userData.image = FirebaseAuth.getInstance().currentUser?.photoUrl.toString()

        setupNavController()
        setupDrawer()
        userName.text = UserManager.userData.id
        Glide.with(navigationView).load(UserManager.userData.image).into(ivHeaderPhoto)
    }

    override fun onStart() {
        super.onStart()
        viewModel.getImageMonster()
    }


    private fun setupNavController() {
        findNavController(R.id.myNavHostFragment).addOnDestinationChangedListener { navController: NavController, _: NavDestination, _: Bundle? ->
            viewModel.currentFragmentType.value = when (navController.currentDestination?.id) {
                R.id.homeFragment -> {
                    val home = binding.bottomNav.menu.findItem(R.id.navigation_home)
                    home.isChecked = true
                    CurrentFragmentType.HOME
                }

                R.id.chatRoomFragment -> CurrentFragmentType.CHATROOM
                R.id.friendFragment -> CurrentFragmentType.FRIEND
                R.id.historyFragment -> CurrentFragmentType.HISTORY
                R.id.profileFragment -> CurrentFragmentType.PROFILE
                else -> viewModel.currentFragmentType.value
            }
        }
    }

    private fun setupDrawer() {
        val navController = this.findNavController(R.id.myNavHostFragment)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = null
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        NavigationUI.setupWithNavController(binding.drawerNavView, navController)
        actionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)

            }
        }.apply {
            binding.drawerLayout.addDrawerListener(this)
            syncState()
        }
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun hidingToolbar() {
        binding.toolbar.visibility = View.GONE
    }

    fun hidingBottommost() {
        binding.bottomNav.visibility = View.GONE
    }

    fun getToolbar() {
        binding.toolbar.visibility = View.VISIBLE
    }

    fun getBottommost() {
        binding.bottomNav.visibility = View.VISIBLE
    }
}

