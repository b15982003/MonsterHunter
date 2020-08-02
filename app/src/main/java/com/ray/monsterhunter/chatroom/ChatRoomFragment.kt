package com.ray.monsterhunter.chatroom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ray.monsterhunter.NavigationDirections
import com.ray.monsterhunter.R

import com.ray.monsterhunter.databinding.ChatRoomFragmentBinding
import com.ray.monsterhunter.ext.getVmFactory
import com.ray.monsterhunter.util.Logger

class ChatRoomFragment : Fragment() {

    private val viewModel by viewModels<ChatRoomViewModel> { getVmFactory() }
    lateinit var binding: ChatRoomFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ChatRoomFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.chatRoomNot.setOnClickListener(){
            findNavController().navigate(R.id.dialogChatRoomFragment)
        }

        var adapter = ChatRoomAdapter(ChatRoomAdapter.OnClickListener {
            findNavController().navigate(NavigationDirections.actionGlobalChatRoomDetail(it))
        },viewModel)

        adapter.setHasStableIds(true)
        binding.chatRoomRecy.adapter = adapter

//        viewModel.livechatRoom.observe(viewLifecycleOwner, Observer {
//            it?.let {
//
//                Logger.d("size = ${viewModel.livechatRoom.value?.size}")
//
//            }
//        })


        return binding.root
    }


}



