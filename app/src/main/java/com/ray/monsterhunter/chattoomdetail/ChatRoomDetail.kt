package com.ray.monsterhunter.chattoomdetail


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ray.monsterhunter.MainActivity
import com.ray.monsterhunter.databinding.ChatRoomDetailFragmentBinding
import com.ray.monsterhunter.ext.getVmFactory


class ChatRoomDetail : Fragment() {


    private val viewModel by viewModels<ChatRoomDetailViewModel> { getVmFactory() }
    lateinit var binding : ChatRoomDetailFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ChatRoomDetailFragmentBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel



        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).hiddingBottomnav()
        (activity as MainActivity).hiddingToolbar()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).getBottomnav()
        (activity as MainActivity).getToolbar()
    }




}
