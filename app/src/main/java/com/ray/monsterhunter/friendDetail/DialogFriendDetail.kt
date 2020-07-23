package com.ray.monsterhunter.friendDetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.viewModels

import com.ray.monsterhunter.R
import com.ray.monsterhunter.databinding.DialogFriendDetailFragmentBinding
import com.ray.monsterhunter.ext.getVmFactory

class DialogFriendDetail : AppCompatDialogFragment()  {

    private val viewModel by viewModels<DialogFriendDetailViewModel> { getVmFactory(DialogFriendDetailArgs.fromBundle(requireArguments()).frienddetail) }
    lateinit var binding : DialogFriendDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DialogFriendDetailFragmentBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }


}
