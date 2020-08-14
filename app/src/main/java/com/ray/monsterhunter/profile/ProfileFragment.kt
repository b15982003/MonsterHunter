package com.ray.monsterhunter.profile

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ray.monsterhunter.LogInActivity
import com.ray.monsterhunter.MainActivity
import com.ray.monsterhunter.MonsterApplication

import com.ray.monsterhunter.R
import com.ray.monsterhunter.databinding.ProfileFragmentBinding
import com.ray.monsterhunter.ext.getVmFactory
import com.ray.monsterhunter.util.Logger
import com.ray.monsterhunter.util.UserManager
import kotlinx.android.synthetic.main.activity_main.*

class ProfileFragment : Fragment() {

    private lateinit var binding: ProfileFragmentBinding

    private val viewModel by viewModels<ProfileViewModel> { getVmFactory() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel

        viewModel.user.observe(viewLifecycleOwner, Observer {
            //draw start
            it?.let { User ->
                (User.armsType.A?.div(60))?.toFloat()?.let {
                    binding.profileArmsARatingBar.setRating(it)
                }
                (User.armsType.B?.div(60))?.toFloat()?.let {
                    it
                    binding.profileArmsBRatingBar.setRating(it)
                }
                (User.armsType.C?.div(60))?.toFloat()?.let {
                    it
                    binding.profileArmsCRatingBar.setRating(it)
                }
                (User.armsType.D?.div(60))?.toFloat()?.let {
                    it
                    binding.profileArmsDRatingBar.setRating(it)
                }
                (User.armsType.E?.div(60))?.toFloat()?.let {
                    it
                    binding.profileArmsERatingBar.setRating(it)
                }
                (User.armsType.F?.div(60))?.toFloat()?.let {
                    it
                    binding.profileArmsFRatingBar.setRating(it)
                }
                (User.armsType.G?.div(60))?.toFloat()?.let {
                    it
                    binding.profileArmsGRatingBar.setRating(it)
                }
                (User.armsType.H?.div(60))?.toFloat()?.let {
                    it
                    binding.profileArmsHRatingBar.setRating(it)
                }
                (User.armsType.I?.div(60))?.toFloat()?.let {
                    it
                    binding.profileArmsIRatingBar.setRating(it)
                }
                (User.armsType.J?.div(60))?.toFloat()?.let {
                    it
                    binding.profileArmsJRatingBar.setRating(it)
                }
                (User.armsType.K?.div(60))?.toFloat()?.let {
                    it
                    binding.profileArmsKRatingBar.setRating(it)
                }
                (User.armsType.L?.div(60))?.toFloat()?.let {
                    it
                    binding.profileArmsLRatingBar.setRating(it)
                }
                (User.armsType.M?.div(60))?.toFloat()?.let {
                    it
                    binding.profileArmsMRatingBar.setRating(it)
                }
                (User.armsType.N?.div(60))?.toFloat()?.let {
                    it
                    binding.profileArmsNRatingBar.setRating(it)
                }

            }
        })
        return binding.root
    }
}


