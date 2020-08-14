package com.ray.monsterhunter.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ray.monsterhunter.R
import com.ray.monsterhunter.databinding.HistoryFragmentBinding
import com.ray.monsterhunter.ext.getVmFactory

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

        viewModel.liveHistory.observe(viewLifecycleOwner, Observer {
            binding.historyDataRecy.layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fade_in)

            if (viewModel.liveHistory.value == listOf<String>()){
                binding.historyListNoValue.visibility = View.VISIBLE
                binding.historyListNoValueText.visibility = View.VISIBLE
            }else{
                binding.historyListNoValue.visibility = View.GONE
                binding.historyListNoValueText.visibility = View.GONE
            }
        })
        return binding.root
    }
}
