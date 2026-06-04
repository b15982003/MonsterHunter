package com.ray.monsterhunter.profile

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
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

class ProfileFragment : Fragment() {
    private lateinit var binding: ProfileFragmentBinding
    private val viewModel by viewModels<ProfileViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = bars.top)
            insets
        }

        viewModel.user.observe(viewLifecycleOwner, Observer {
            it?.let { user ->
                (user.armsType.A?.div(60))?.toFloat()?.let {it ->
                    binding.profileArmsARatingBar.rating = it
                }
                (user.armsType.B?.div(60))?.toFloat()?.let { it ->
                    binding.profileArmsBRatingBar.rating = it
                }
                (user.armsType.C?.div(60))?.toFloat()?.let {it ->
                    binding.profileArmsCRatingBar.rating = it
                }
                (user.armsType.D?.div(60))?.toFloat()?.let {it ->
                    binding.profileArmsDRatingBar.rating = it
                }
                (user.armsType.E?.div(60))?.toFloat()?.let {it ->
                    binding.profileArmsERatingBar.rating = it
                }
                (user.armsType.F?.div(60))?.toFloat()?.let {it ->
                    binding.profileArmsFRatingBar.rating = it
                }
                (user.armsType.G?.div(60))?.toFloat()?.let {it ->
                    binding.profileArmsGRatingBar.rating = it
                }
                (user.armsType.H?.div(60))?.toFloat()?.let {it ->
                    binding.profileArmsHRatingBar.rating = it
                }
                (user.armsType.I?.div(60))?.toFloat()?.let {it ->
                    binding.profileArmsIRatingBar.rating = it
                }
                (user.armsType.J?.div(60))?.toFloat()?.let {it ->
                    binding.profileArmsJRatingBar.rating = it
                }
                (user.armsType.K?.div(60))?.toFloat()?.let {it ->
                    binding.profileArmsKRatingBar.rating = it
                }
                (user.armsType.L?.div(60))?.toFloat()?.let {it ->
                    binding.profileArmsLRatingBar.rating = it
                }
                (user.armsType.M?.div(60))?.toFloat()?.let {it ->
                    binding.profileArmsMRatingBar.rating = it
                }
                (user.armsType.N?.div(60))?.toFloat()?.let {it ->
                    binding.profileArmsNRatingBar.rating = it
                }
            }
        })
        return binding.root
    }
}


