package com.ray.monsterhunter.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import com.ray.monsterhunter.R
import com.ray.monsterhunter.databinding.FragmentPostChatRoomWorkMangerInfoBinding

class PostChatRoomWorkMangerInfo : AppCompatDialogFragment() {

    lateinit var binding: FragmentPostChatRoomWorkMangerInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogPost)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPostChatRoomWorkMangerInfoBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.chatRoomPostTimeOut.setOnClickListener(){
            dismiss()
        }


        return binding.root
    }

}
