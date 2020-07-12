package com.ray.monsterhunter.chatroom

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels

import com.ray.monsterhunter.R
import com.ray.monsterhunter.databinding.ChatRoomFragmentBinding
import com.ray.monsterhunter.ext.getVmFactory

class ChatRoomFragment : Fragment() {

    private val viewModel by viewModels<ChatRoomViewModel> {getVmFactory()}
    lateinit var binding : ChatRoomFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ChatRoomFragmentBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.chatRoomRecy.adapter = ChatRoomAdapter(ChatRoomAdapter.OnClickListener{

        })
        return binding.root
        }


    }



