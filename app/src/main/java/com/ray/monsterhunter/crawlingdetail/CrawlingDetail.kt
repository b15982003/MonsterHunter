package com.ray.monsterhunter.crawlingdetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ray.monsterhunter.MainActivity

import com.ray.monsterhunter.R
import com.ray.monsterhunter.databinding.CrawlingDetailFragmentBinding
import com.ray.monsterhunter.ext.getVmFactory

class CrawlingDetail : Fragment() {


    private val viewModel by viewModels<CrawlingDetailViewModel> { getVmFactory(CrawlingDetailArgs.fromBundle(requireArguments()).crawlingDetail) }
    lateinit var binding : CrawlingDetailFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = CrawlingDetailFragmentBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.crawlingDetailBack.setOnClickListener(){
            findNavController().navigateUp()
        }


        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).hiddingToolbar()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).getToolbar()
    }

}
