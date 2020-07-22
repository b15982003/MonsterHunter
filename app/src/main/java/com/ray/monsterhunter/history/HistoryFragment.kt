package com.ray.monsterhunter.history

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

import com.ray.monsterhunter.R
import com.ray.monsterhunter.databinding.HistoryFragmentBinding
import com.ray.monsterhunter.ext.getVmFactory
import com.ray.monsterhunter.network.LoadApiStatus

class HistoryFragment : Fragment() {


    private val viewModel by viewModels<HistoryViewModel> { getVmFactory() }
    lateinit var binding: HistoryFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = HistoryFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = HistoryAdapter(HistoryAdapter.OnClickListener{
        })
        binding.historyDataRecy.adapter = adapter

        viewModel.status.observe(viewLifecycleOwner, Observer {
            if(viewModel.status.value == LoadApiStatus.LOADING){
                binding.historyLoadingImage.visibility = View.VISIBLE
            }else{
                binding.historyLoadingImage.visibility = View.INVISIBLE
            }
        })




        return binding.root
    }

}
