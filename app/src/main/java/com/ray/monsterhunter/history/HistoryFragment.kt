package com.ray.monsterhunter.history

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel

import com.ray.monsterhunter.R
import com.ray.monsterhunter.databinding.HistoryFragmentBinding
import com.ray.monsterhunter.ext.getVmFactory

class HistoryFragment : Fragment() {


    private val viewModel by viewModels<HistoryViewModel> { getVmFactory() }
    lateinit var binding : HistoryFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = HistoryFragmentBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

}
