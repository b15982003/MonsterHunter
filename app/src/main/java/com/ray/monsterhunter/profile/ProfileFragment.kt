package com.ray.monsterhunter.profile

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ray.monsterhunter.LogInActivity
import com.ray.monsterhunter.MainActivity
import com.ray.monsterhunter.MonsterApplication

import com.ray.monsterhunter.R
import com.ray.monsterhunter.databinding.ProfileFragmentBinding
import com.ray.monsterhunter.ext.getVmFactory
import com.ray.monsterhunter.util.Logger
import com.ray.monsterhunter.util.UserManager
import kotlinx.android.synthetic.main.activity_main.*

class ProfileFragment : Fragment() {

    private lateinit var binding : ProfileFragmentBinding

    private val viewModel by viewModels<ProfileViewModel>{ getVmFactory() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ProfileFragmentBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel

        binding.profileSignOut.setOnClickListener{
            viewModel.signOut()
            Handler().postDelayed({
                startActivity(Intent(MonsterApplication.instance, LogInActivity::class.java))
            },1000)
        }

        viewModel.user.observe(viewLifecycleOwner, Observer {
            Logger.d("userIn${viewModel.user.value}")
            Logger.d("userMander${UserManager.userData.email}")
        })

        return binding.root
    }


}
