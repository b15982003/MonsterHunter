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
            } else {
                viewModel.cencelFollow()
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

            if (viewModel.user.value?.armsType?.A!! < 900) {
                binding.friendDetailDialogArmsARatingBarGod.visibility = View.INVISIBLE
            } else {
                binding.friendDetailDialogArmsARatingBarGod.visibility = View.VISIBLE
            }

            if (viewModel.user.value?.armsType?.B!! < 900) {
                binding.friendDetailDialogArmsBRatingBarGod.visibility = View.INVISIBLE
            } else {
                binding.friendDetailDialogArmsBRatingBarGod.visibility = View.VISIBLE
            }

            if (viewModel.user.value?.armsType?.C!! < 900) {
                binding.friendDetailDialogArmsCRatingBarGod.visibility = View.INVISIBLE
            } else {
                binding.friendDetailDialogArmsCRatingBarGod.visibility = View.VISIBLE
            }

            if (viewModel.user.value?.armsType?.D!! < 900) {
                binding.friendDetailDialogArmsDRatingBarGod.visibility = View.INVISIBLE
            } else {
                binding.friendDetailDialogArmsDRatingBarGod.visibility = View.VISIBLE
            }

            if (viewModel.user.value?.armsType?.E!! < 900) {
                binding.friendDetailDialogArmsERatingBarGod.visibility = View.INVISIBLE
            } else {
                binding.friendDetailDialogArmsERatingBarGod.visibility = View.VISIBLE
            }

            if (viewModel.user.value?.armsType?.F!! < 900) {
                binding.friendDetailDialogArmsFRatingBarGod.visibility = View.INVISIBLE
            } else {
                binding.friendDetailDialogArmsFRatingBarGod.visibility = View.VISIBLE
            }

            if (viewModel.user.value?.armsType?.G!! < 900) {
                binding.friendDetailDialogArmsGRatingBarGod.visibility = View.INVISIBLE
            } else {
                binding.friendDetailDialogArmsGRatingBarGod.visibility = View.VISIBLE
            }

            if (viewModel.user.value?.armsType?.H!! < 900) {
                binding.friendDetailDialogArmsHRatingBarGod.visibility = View.INVISIBLE
            } else {
                binding.friendDetailDialogArmsHRatingBarGod.visibility = View.VISIBLE
            }

            if (viewModel.user.value?.armsType?.I!! < 900) {
                binding.friendDetailDialogArmsIRatingBarGod.visibility = View.INVISIBLE
            } else {
                binding.friendDetailDialogArmsIRatingBarGod.visibility = View.VISIBLE
            }

            if (viewModel.user.value?.armsType?.J!! < 900) {
                binding.friendDetailDialogArmsJRatingBarGod.visibility = View.INVISIBLE
            } else {
                binding.friendDetailDialogArmsJRatingBarGod.visibility = View.VISIBLE
            }

            if (viewModel.user.value?.armsType?.K!! < 900) {
                binding.friendDetailDialogArmsKRatingBarGod.visibility = View.INVISIBLE
            } else {
                binding.friendDetailDialogArmsKRatingBarGod.visibility = View.VISIBLE
            }

            if (viewModel.user.value?.armsType?.L!! < 900) {
                binding.friendDetailDialogArmsLRatingBarGod.visibility = View.INVISIBLE
            } else {
                binding.friendDetailDialogArmsLRatingBarGod.visibility = View.VISIBLE
            }

            if (viewModel.user.value?.armsType?.M!! < 900) {
                binding.friendDetailDialogArmsMRatingBarGod.visibility = View.INVISIBLE
            } else {
                binding.friendDetailDialogArmsMRatingBarGod.visibility = View.VISIBLE
            }

            if (viewModel.user.value?.armsType?.N!! < 900) {
                binding.friendDetailDialogArmsNRatingBarGod.visibility = View.INVISIBLE
            } else {
                binding.friendDetailDialogArmsNRatingBarGod.visibility = View.VISIBLE
            }
        })

        return binding.root
    }


}
