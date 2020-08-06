package com.ray.monsterhunter.friendDetail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ray.monsterhunter.R
import com.ray.monsterhunter.databinding.DialogFriendDetailFragmentBinding
import com.ray.monsterhunter.ext.getVmFactory


class DialogFriendDetail : AppCompatDialogFragment() {

    private val viewModel by viewModels<DialogFriendDetailViewModel> {
        getVmFactory(
            DialogFriendDetailArgs.fromBundle(requireArguments()).friendDetail
        )
    }
    lateinit var binding: DialogFriendDetailFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogPost)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DialogFriendDetailFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.friendDetailDialogFollow.setBackgroundResource(R.drawable.friend_detail_dialog_follow)

        binding.friendDetailDialogFollow.setOnClickListener() {
            if (viewModel.follow.value == false) {
                viewModel.getFollow()
                viewModel.postFriend()
            } else {
                viewModel.cencelFollow()
                viewModel.cancelFriend()
            }
        }

        viewModel.follow.observe(viewLifecycleOwner, Observer {
            if (viewModel.follow.value == true) {
                binding.friendDetailDialogFollow.setBackgroundResource(R.drawable.friend_detail_dialog_getfollow)
            } else {
                binding.friendDetailDialogFollow.setBackgroundResource(R.drawable.friend_detail_dialog_follow)
            }
        })

        viewModel.leave.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigateUp()
                viewModel.onLeft()
            }
        })

        viewModel.user.observe(viewLifecycleOwner, Observer {
            if (viewModel.user.value?.track == "true"){
                viewModel.getFollow()
            }
        })
        return binding.root
    }
}
