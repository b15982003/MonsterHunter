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

        binding.profileSignOut.setOnClickListener {
            viewModel.signOut()
            Handler().postDelayed({
                startActivity(Intent(MonsterApplication.instance, LogInActivity::class.java))
            }, 1000)
        }


        viewModel.user.observe(viewLifecycleOwner, Observer {
            viewModel.userA.value = (viewModel.user.value?.armsType?.A?.div(200))?.toFloat()
            viewModel.userB.value = (viewModel.user.value?.armsType?.B?.div(200))?.toFloat()
            viewModel.userC.value = (viewModel.user.value?.armsType?.C?.div(200))?.toFloat()
            viewModel.userD.value = (viewModel.user.value?.armsType?.D?.div(200))?.toFloat()
            viewModel.userE.value = (viewModel.user.value?.armsType?.E?.div(200))?.toFloat()
            viewModel.userF.value = (viewModel.user.value?.armsType?.F?.div(200))?.toFloat()
            viewModel.userG.value = (viewModel.user.value?.armsType?.G?.div(200))?.toFloat()
            viewModel.userH.value = (viewModel.user.value?.armsType?.H?.div(200))?.toFloat()
            viewModel.userI.value = (viewModel.user.value?.armsType?.I?.div(200))?.toFloat()
            viewModel.userJ.value = (viewModel.user.value?.armsType?.J?.div(200))?.toFloat()
            viewModel.userK.value = (viewModel.user.value?.armsType?.K?.div(200))?.toFloat()
            viewModel.userL.value = (viewModel.user.value?.armsType?.L?.div(200))?.toFloat()
            viewModel.userM.value = (viewModel.user.value?.armsType?.M?.div(200))?.toFloat()
            viewModel.userN.value = (viewModel.user.value?.armsType?.N?.div(200))?.toFloat()

            viewModel.userA.value?.let { it1 -> binding.profileArmsARatingBar.setRating(it1) }
            viewModel.userB.value?.let { it1 -> binding.profileArmsBRatingBar.setRating(it1) }
            viewModel.userC.value?.let { it1 -> binding.profileArmsCRatingBar.setRating(it1) }
            viewModel.userD.value?.let { it1 -> binding.profileArmsDRatingBar.setRating(it1) }
            viewModel.userE.value?.let { it1 -> binding.profileArmsERatingBar.setRating(it1) }
            viewModel.userF.value?.let { it1 -> binding.profileArmsFRatingBar.setRating(it1) }
            viewModel.userG.value?.let { it1 -> binding.profileArmsGRatingBar.setRating(it1) }
            viewModel.userH.value?.let { it1 -> binding.profileArmsHRatingBar.setRating(it1) }
            viewModel.userI.value?.let { it1 -> binding.profileArmsIRatingBar.setRating(it1) }
            viewModel.userJ.value?.let { it1 -> binding.profileArmsJRatingBar.setRating(it1) }
            viewModel.userK.value?.let { it1 -> binding.profileArmsKRatingBar.setRating(it1) }
            viewModel.userL.value?.let { it1 -> binding.profileArmsLRatingBar.setRating(it1) }
            viewModel.userM.value?.let { it1 -> binding.profileArmsMRatingBar.setRating(it1) }
            viewModel.userN.value?.let { it1 -> binding.profileArmsNRatingBar.setRating(it1) }

            if (viewModel.user.value?.armsType?.A!! < 1000){
                binding.profileArmsARatingBarGod.visibility = View.INVISIBLE
            }else{
                binding.profileArmsARatingBarGod.visibility = View.VISIBLE
            }

            if (viewModel.user.value?.armsType?.B!! < 1000){
                binding.profileArmsBRatingBarGod.visibility = View.INVISIBLE
            }else{
                binding.profileArmsBRatingBarGod.visibility = View.VISIBLE
            }


        })





        return binding.root
    }
}


