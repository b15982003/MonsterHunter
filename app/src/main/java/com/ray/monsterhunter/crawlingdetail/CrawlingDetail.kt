package com.ray.monsterhunter.crawlingdetail

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ray.monsterhunter.MainActivity
import com.ray.monsterhunter.databinding.CrawlingDetailFragmentBinding
import com.ray.monsterhunter.ext.getVmFactory
import com.ray.monsterhunter.util.Logger
import com.ray.monsterhunter.util.TimeUtil
import java.util.*

class CrawlingDetail : Fragment() {

    private val viewModel by viewModels<CrawlingDetailViewModel> {
        getVmFactory(
            CrawlingDetailArgs.fromBundle(
                requireArguments()
            ).crawlingDetail
        )
    }
    lateinit var binding: CrawlingDetailFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = CrawlingDetailFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.crawlingDetailBack.setOnClickListener() {
            findNavController().navigateUp()
        }

        binding.crawlingDetailSentMessage.setOnClickListener() {
            viewModel.message.value?.let { it1 -> viewModel.leaveMessage(it1) }
            Handler().postDelayed({
                binding.crawlingDetailItemEditText.text.clear()
            }, 500)
        }

        val adapter = CrawlingDetailAdapter(CrawlingDetailAdapter.OnClickListener {
        })
        //let flash out
        adapter.setHasStableIds(true)

        binding.crawlingDetailLeaveMessageRecy.adapter = adapter
        // time change to UI
        val AllStampTimeToDate =
            viewModel.crawling.value?.createTime?.let { TimeUtil.AllStampToDate(it, Locale.TAIWAN) }

        binding.crawlingDetailCreateTime.text = AllStampTimeToDate

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
