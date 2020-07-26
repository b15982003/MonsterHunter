package com.ray.monsterhunter.crawlingdetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ray.monsterhunter.R

class CrawlingDetail : Fragment() {

    companion object {
        fun newInstance() = CrawlingDetail()
    }

    private lateinit var viewModel: CrawlingDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.crawling_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CrawlingDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
