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
            viewModel.userA.value = (viewModel.user.value?.armsType?.A?.div(60))?.toFloat()
            viewModel.userB.value = (viewModel.user.value?.armsType?.B?.div(60))?.toFloat()
            viewModel.userC.value = (viewModel.user.value?.armsType?.C?.div(60))?.toFloat()
            viewModel.userD.value = (viewModel.user.value?.armsType?.D?.div(60))?.toFloat()
            viewModel.userE.value = (viewModel.user.value?.armsType?.E?.div(60))?.toFloat()
            viewModel.userF.value = (viewModel.user.value?.armsType?.F?.div(60))?.toFloat()
            viewModel.userG.value = (viewModel.user.value?.armsType?.G?.div(60))?.toFloat()
            viewModel.userH.value = (viewModel.user.value?.armsType?.H?.div(60))?.toFloat()
            viewModel.userI.value = (viewModel.user.value?.armsType?.I?.div(60))?.toFloat()
            viewModel.userJ.value = (viewModel.user.value?.armsType?.J?.div(60))?.toFloat()
            viewModel.userK.value = (viewModel.user.value?.armsType?.K?.div(60))?.toFloat()
            viewModel.userL.value = (viewModel.user.value?.armsType?.L?.div(60))?.toFloat()
            viewModel.userM.value = (viewModel.user.value?.armsType?.M?.div(60))?.toFloat()
            viewModel.userN.value = (viewModel.user.value?.armsType?.N?.div(60))?.toFloat()

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

            if (viewModel.user.value?.armsType?.A!! < 900){
                binding.profileArmsARatingBarGod.visibility = View.INVISIBLE
            }else{
                binding.profileArmsARatingBarGod.visibility = View.VISIBLE
            }

            if (viewModel.user.value?.armsType?.B!! < 900){
                binding.profileArmsBRatingBarGod.visibility = View.INVISIBLE
            }else{
                binding.profileArmsBRatingBarGod.visibility = View.VISIBLE
            }

            if (viewModel.user.value?.armsType?.C!! < 900){
                binding.profileArmsCRatingBarGod.visibility = View.INVISIBLE
            }else{
                binding.profileArmsCRatingBarGod.visibility = View.VISIBLE
            }

            if (viewModel.user.value?.armsType?.D!! < 900){
                binding.profileArmsDRatingBarGod.visibility = View.INVISIBLE
            }else{
                binding.profileArmsDRatingBarGod.visibility = View.VISIBLE
            }

            if (viewModel.user.value?.armsType?.E!! < 900){
                binding.profileArmsERatingBarGod.visibility = View.INVISIBLE
            }else{
                binding.profileArmsERatingBarGod.visibility = View.VISIBLE
            }

            if (viewModel.user.value?.armsType?.F!! < 900){
                binding.profileArmsFRatingBarGod.visibility = View.INVISIBLE
            }else{
                binding.profileArmsFRatingBarGod.visibility = View.VISIBLE
            }

            if (viewModel.user.value?.armsType?.G!! < 900){
                binding.profileArmsGRatingBarGod.visibility = View.INVISIBLE
            }else{
                binding.profileArmsGRatingBarGod.visibility = View.VISIBLE
            }

            if (viewModel.user.value?.armsType?.H!! < 900){
                binding.profileArmsHRatingBarGod.visibility = View.INVISIBLE
            }else{
                binding.profileArmsHRatingBarGod.visibility = View.VISIBLE
            }

            if (viewModel.user.value?.armsType?.I!! < 900){
                binding.profileArmsIRatingBarGod.visibility = View.INVISIBLE
            }else{
                binding.profileArmsIRatingBarGod.visibility = View.VISIBLE
            }

            if (viewModel.user.value?.armsType?.J!! < 900){
                binding.profileArmsJRatingBarGod.visibility = View.INVISIBLE
            }else{
                binding.profileArmsJRatingBarGod.visibility = View.VISIBLE
            }

            if (viewModel.user.value?.armsType?.K!! < 900){
                binding.profileArmsKRatingBarGod.visibility = View.INVISIBLE
            }else{
                binding.profileArmsKRatingBarGod.visibility = View.VISIBLE
            }

            if (viewModel.user.value?.armsType?.L!! < 900){
                binding.profileArmsLRatingBarGod.visibility = View.INVISIBLE
            }else{
                binding.profileArmsLRatingBarGod.visibility = View.VISIBLE
            }

            if (viewModel.user.value?.armsType?.M!! < 900){
                binding.profileArmsMRatingBarGod.visibility = View.INVISIBLE
            }else{
                binding.profileArmsMRatingBarGod.visibility = View.VISIBLE
            }

            if (viewModel.user.value?.armsType?.N!! < 900){
                binding.profileArmsNRatingBarGod.visibility = View.INVISIBLE
            }else{
                binding.profileArmsNRatingBarGod.visibility = View.VISIBLE
            }




        })





        return binding.root
    }
}


