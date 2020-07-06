package com.ray.monsterhunter


import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.ray.monsterhunter.databinding.ActivityMainBinding
import com.ray.monsterhunter.ext.getVmFactory
import com.ray.monsterhunter.util.CurrentFragmentType



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

        binding.bottomNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        binding.drawerNavView.setNavigationItemSelectedListener(drawerNavigationItemSelectedListener)

        //addData()
        setupNavController()
        setupDrawer()

    }

    private fun setupNavController() {
        findNavController(R.id.myNavHostFragment).addOnDestinationChangedListener { navController: NavController, _: NavDestination, _: Bundle? ->
            viewModel.currentFragmentType.value = when (navController.currentDestination?.id) {
                R.id.homeFragment -> CurrentFragmentType.HOME
                R.id.chatRoomFragment -> CurrentFragmentType.CHATROOM
                R.id.friendFragment -> CurrentFragmentType.FRIEND
                R.id.historyFragment -> CurrentFragmentType.HISTORY
                R.id.profileFragment -> CurrentFragmentType.PROFILE
                else -> viewModel.currentFragmentType.value
            }
        }
    }

    private fun setupDrawer() {

        // set up toolbar
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

        //PO文章
//        fun addData() {
//            val articles = FirebaseFirestore.getInstance()
//                .collection("articles")
//            val document = articles.document()
//            val data = hashMapOf(
//                "author" to hashMapOf(
//                    "email" to "wayne@school.appworks.tw",
//                    "id" to "waynechen323",
//                    "name" to "AKA小安老師"
//                ),
//                "title" to "IU「亂穿」竟美出新境界！笑稱自己品味奇怪　網笑：靠顏值撐住女神氣場",
//                "content" to "南韓歌手IU（李知恩）無論在歌唱方面或是近期的戲劇作品都有亮眼的成績，但俗話說人無完美、美玉微瑕，曾再跟工作人員的互動影片中坦言自己品味很奇怪，近日在IG上分享了宛如「媽媽們青春時代的玉女歌手」超復古穿搭造型，卻意外美出新境界。",
//                "createdTime" to Calendar.getInstance()
//                    .timeInMillis,
//                "documentid" to document.id,
//                "tag" to "Beauty"
//            )
//            document.set(data as Map<String, Any>)
//        }
    override fun onBackPressed() {

        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
    }

