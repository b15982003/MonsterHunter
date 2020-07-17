package com.ray.monsterhunter.chattoomdetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ray.monsterhunter.R

class ChatRoomDetailScore : Fragment() {

    companion object {
        fun newInstance() = ChatRoomDetailScore()
    }

    private lateinit var viewModel: ChatRoomDetailScoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chat_room_detail_score_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ChatRoomDetailScoreViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
