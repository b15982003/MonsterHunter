package com.ray.monsterhunter.friend.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.NavigationDirections
import com.ray.monsterhunter.data.source.MonsterRepository
import com.ray.monsterhunter.databinding.FriendFragmentBinding
import com.ray.monsterhunter.databinding.FriendItemFragmentBinding
import com.ray.monsterhunter.databinding.ItemFriendBinding
import com.ray.monsterhunter.ext.getVmFactory
import com.ray.monsterhunter.friend.FriendTypeFilter


class FriendItemFragment(private val friendTypeFilter: FriendTypeFilter) : Fragment() {


    private val viewModel by viewModels<FriendItemViewModel> { getVmFactory(friendTypeFilter) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FriendItemFragmentBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        var adapter = FriendItemAdapter(FriendItemAdapter.OnClickListener {

            findNavController().navigate(NavigationDirections.actionGlobalDialogFriendDetail(it))
        })

        binding.friendListRecy.adapter = adapter

        viewModel.userList.observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })

        return binding.root
    }
}