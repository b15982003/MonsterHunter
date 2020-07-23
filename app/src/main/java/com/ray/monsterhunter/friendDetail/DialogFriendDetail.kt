package com.ray.monsterhunter.friendDetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment

import com.ray.monsterhunter.R

class DialogFriendDetail : AppCompatDialogFragment()  {


    private lateinit var viewModel: DialogFriendDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_friend_detail_fragment, container, false)
    }


}
