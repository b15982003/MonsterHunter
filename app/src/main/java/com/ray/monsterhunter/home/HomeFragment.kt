package com.ray.monsterhunter.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ray.monsterhunter.databinding.HomeFragmentBinding
import com.ray.monsterhunter.ext.getVmFactory
import com.ray.monsterhunter.util.Logger
import com.ray.monsterhunter.util.TimeUtil
import java.util.*


class HomeFragment : Fragment() {

    private lateinit var binding : HomeFragmentBinding
    private val viewModel by viewModels<HomeViewModel> { getVmFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = HomeFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.homeDataRecy.adapter = HomeCrawlingAdapter(HomeCrawlingAdapter.OnClickListener{

        })

        binding.homeActivityRecy.adapter = HomeActivityAdapter(HomeActivityAdapter.OnClickListener{

        })



        return binding.root
    }
}
