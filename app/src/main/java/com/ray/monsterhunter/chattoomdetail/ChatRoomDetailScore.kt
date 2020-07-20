package com.ray.monsterhunter.chattoomdetail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ray.monsterhunter.MainActivity
import com.ray.monsterhunter.databinding.ChatRoomDetailScoreFragmentBinding
import com.ray.monsterhunter.ext.getVmFactory
import com.ray.monsterhunter.util.Logger


class ChatRoomDetailScore : Fragment() {


    private val viewModel by viewModels<ChatRoomDetailScoreViewModel> {
        getVmFactory(
            ChatRoomDetailScoreArgs.fromBundle(requireArguments()).chatRoomDetail
        )
    }
    lateinit var binding: ChatRoomDetailScoreFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = ChatRoomDetailScoreFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.chatRoomScoreSentButton.setOnClickListener() {
            findNavController().navigateUp()
        }

        viewModel.chatRoom.observe(viewLifecycleOwner, Observer {
            Logger.d("chatRoomValue${viewModel.chatRoom.value}")
        })

        viewModel.user1.observe(viewLifecycleOwner, Observer {
            Logger.d("user111111111111${viewModel.user1.value}")
        })
        viewModel.user2.observe(viewLifecycleOwner, Observer {
            Logger.d("user2222222222${viewModel.user2.value}")
        })
        viewModel.user3.observe(viewLifecycleOwner, Observer {
            Logger.d("user3333333333${viewModel.user3.value}")
        })
        viewModel.user4.observe(viewLifecycleOwner, Observer {
            Logger.d("user4444444444${viewModel.user4.value}")
        })

//        chatRoomViewModel.chatRoom.observe(viewLifecycleOwner, Observer {
//            Logger.d("finish ${chatRoomViewModel.chatRoom.value?.finishTime}")
//        })


        return binding.root
    }

}
