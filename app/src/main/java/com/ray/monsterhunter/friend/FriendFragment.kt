package com.ray.monsterhunter.friend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.ray.monsterhunter.databinding.FriendFragmentBinding
import com.ray.monsterhunter.ext.getVmFactory

class FriendFragment : Fragment() {

    private val viewModel by viewModels<FriendViewModel> { getVmFactory() }
    lateinit var  binding : FriendFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FriendFragmentBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.pager.let {
            binding.tabLayout.setupWithViewPager(it)
            it.adapter = FriendAdapter(childFragmentManager)
            it.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))
        }
        return binding.root
    }
}


