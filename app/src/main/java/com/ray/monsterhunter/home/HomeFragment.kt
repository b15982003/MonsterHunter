package com.ray.monsterhunter.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.ray.monsterhunter.NavigationDirections
import com.ray.monsterhunter.R
import com.ray.monsterhunter.databinding.HomeFragmentBinding
import com.ray.monsterhunter.ext.getVmFactory
import com.ray.monsterhunter.network.LoadApiStatus
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

        viewModel.status.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (viewModel.status.value == LoadApiStatus.LOADING){
                binding.homeLoadingImage.visibility = View.VISIBLE
            }else{
                binding.homeLoadingImage.visibility = View.INVISIBLE
            }
        })


        binding.activityNot.setOnClickListener(){
            findNavController().navigate(R.id.dialogPostFragment)
        }

        binding.homeDataRecy.adapter = HomeCrawlingAdapter(HomeCrawlingAdapter.OnClickListener{
            findNavController().navigate(NavigationDirections.actionGlobalCrawlingDetail(it))

        })
        HomeCrawlingAdapter(HomeCrawlingAdapter.OnClickListener {  }).notifyDataSetChanged()

        binding.homeActivityRecy.adapter = HomeActivityAdapter(HomeActivityAdapter.OnClickListener{

        })



        return binding.root
    }
}
