package com.ray.monsterhunter.chattoomdetail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ray.monsterhunter.databinding.ChatRoomDetailScoreFragmentBinding
import com.ray.monsterhunter.ext.getVmFactory


class ChatRoomDetailScore : Fragment() {


    private val viewModel by viewModels<ChatRoomDetailScoreViewModel> { getVmFactory() }
//    private val chatRoomViewModel by  viewModels<ChatRoomDetailViewModel> { getVmFactory() }
//    lateinit var chatRoomDetailViewModel : ChatRoomDetailViewModel
    lateinit var binding : ChatRoomDetailScoreFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = ChatRoomDetailScoreFragmentBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

//        val chatRoomViewModel = ChatRoomDetailViewModel()
//
//
//
//        chatRoomViewModel.chatRoom.observe(viewLifecycleOwner, Observer {
//            Logger.d("finish ${chatRoomViewModel.chatRoom.value?.finishTime}")
//        })



        return binding.root
    }

}
