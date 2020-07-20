package com.ray.monsterhunter.chattoomdetail


import android.annotation.SuppressLint
import com.ray.monsterhunter.R
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ray.monsterhunter.MainActivity
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.NavigationDirections
import com.ray.monsterhunter.databinding.ChatRoomDetailFragmentBinding
import com.ray.monsterhunter.ext.getVmFactory

import com.ray.monsterhunter.util.UserManager


class ChatRoomDetail : Fragment() {


    private val viewModel by viewModels<ChatRoomDetailViewModel> { getVmFactory(ChatRoomDetailArgs.fromBundle(requireArguments()).chatRoom) }
    lateinit var binding : ChatRoomDetailFragmentBinding


    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ChatRoomDetailFragmentBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.chatRoomDetailStartButton.visibility = View.GONE
        binding.chatRoomDetailEndButton.visibility = View.GONE
        binding.chatRoomDetailStartBackground.visibility = View.GONE
        binding.chatRoomDetailMinNumber.visibility = View.GONE
        binding.chatRoomDetailSecNumber.visibility = View.GONE
        binding.chatRoomDetailLine.visibility = View.GONE
        binding.chatRoomDetailMissionTypeBackground.visibility = View.GONE
        binding.chatRoomDetailMissionTypeSuccess.visibility = View.GONE
        binding.chatRoomDetailMissionTypeFail.visibility = View.GONE
        binding.chatRoomDetailTextMessageRecy.adapter = ChatRoomDetailAdapter(ChatRoomDetailAdapter.OnClickListener{
        })
        binding.chatRoomDetailArmsImage.setImageResource(R.drawable.ic_arms_spear)

        binding.chatRoomDetailReadyButton.setOnClickListener(){
            if(viewModel.chatRoom.value?.userId == UserManager.userData.id){
                if (viewModel.ready.value == false){
                    viewModel.getready()
                    binding.chatRoomDetailStartButton.visibility = View.VISIBLE
                    binding.chatRoomDetailStartBackground.visibility = View.VISIBLE
                    binding.chatRoomDetailMinNumber.visibility = View.VISIBLE
                    binding.chatRoomDetailSecNumber.visibility = View.VISIBLE
                    binding.chatRoomDetailLine.visibility = View.VISIBLE
                    if(viewModel.timming.value == true){
                        binding.chatRoomDetailEndButton.visibility = View.VISIBLE
                    }
                }else{
                    viewModel.endreadt()
                    binding.chatRoomDetailStartButton.visibility = View.GONE
                    binding.chatRoomDetailStartBackground.visibility = View.GONE
                    binding.chatRoomDetailEndButton.visibility = View.GONE
                    binding.chatRoomDetailMinNumber.visibility = View.GONE
                    binding.chatRoomDetailSecNumber.visibility = View.GONE
                    binding.chatRoomDetailLine.visibility = View.GONE
                }
            }else{
                Toast.makeText(MonsterApplication.instance,"請找房主趕快開始",Toast.LENGTH_SHORT).show()
            }

        }

        binding.chatRoomDetailStartButton.setOnClickListener(){
            viewModel.startTimming()
            binding.chatRoomDetailStartButton.visibility = View.GONE
            binding.chatRoomDetailEndButton.visibility = View.VISIBLE

        }

        binding.chatRoomDetailEndButton.setOnClickListener(){
            viewModel.chatRoom.value?.finishTime = viewModel.timeCheck
            viewModel.endTimming()
            binding.chatRoomDetailEndButton.visibility = View.GONE
            binding.chatRoomDetailMissionTypeSuccess.visibility = View.VISIBLE
            binding.chatRoomDetailMissionTypeFail.visibility = View.VISIBLE
            binding.chatRoomDetailMissionTypeBackground.visibility = View.VISIBLE

        }

        binding.chatRoomDetailMissionTypeSuccess.setOnClickListener(){

            viewModel.chatRoom.value?.missionResult = "true"
            viewModel.chatRoom.value?.endToScore = "true"
            viewModel.updateChatRoomInfo()
            Handler().postDelayed({
                findNavController().navigate(NavigationDirections.actionGlobalChatRoomDetailScore(viewModel.chatRoom.value!!))
            },500)
        }

        binding.chatRoomDetailMissionTypeFail.setOnClickListener(){
            viewModel.chatRoom.value?.missionResult = "false"
            viewModel.chatRoom.value?.endToScore = "true"
            viewModel.updateChatRoomInfo()
            Handler().postDelayed({
                findNavController().navigate(NavigationDirections.actionGlobalChatRoomDetailScore(viewModel.chatRoom.value!!))
            },500)
        }



        binding.chatRoomDetailSentMessage.setOnClickListener(){
            viewModel.message.value?.let { it1 -> viewModel.sentMessage(it1) }
            Handler().postDelayed({
                binding.chatRoomDetailItemEditText.text.clear()
            },500)
        }

        binding.chatRoomDetailToolbarBack.setOnClickListener(){

            viewModel.outLeave()
            viewModel.userArmsType.value?.let { it1 -> viewModel.cancelUser(it1) }
            Handler().postDelayed({
                findNavController().navigateUp()
            },500)

        }

        viewModel.chatRoom.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.teammateList = it.teammate
            }
        })

        viewModel.emptySeat.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                viewModel.teammateList.add(UserManager.userData.email.toString())
            }
        })

        viewModel.leave.observe(viewLifecycleOwner, Observer {
            if(it == true){
                Toast.makeText(MonsterApplication.instance,"人員已經滿了",Toast.LENGTH_LONG).show()
                findNavController().navigateUp()
                viewModel.getOutFinish()
            }
        })

        viewModel.isGoon.observe(viewLifecycleOwner, Observer {
            if (it == true){
                viewModel.teammateList.remove(UserManager.userData.email.toString())
            }
        })

        viewModel.timeSec.observe(viewLifecycleOwner, Observer {
            binding.chatRoomDetailSecNumber.text = (viewModel.timeCheck%60).toString()
            binding.chatRoomDetailMinNumber.text = (viewModel.timeCheck/60).toString()
        })

        viewModel.liveChatRoom.observe(viewLifecycleOwner, Observer {
            if (viewModel.chatRoom.value?.userId != UserManager.userData.id && viewModel.liveChatRoom.value?.endToScore == "true"){
                Handler().postDelayed({
                    findNavController().navigate(NavigationDirections.actionGlobalChatRoomDetailScore(viewModel.chatRoom.value!!))
                },500)
            }
        })

        viewModel.userArms.observe(viewLifecycleOwner, Observer {
            viewModel.userArmsType.value?.let { it1 -> viewModel.getUserArms(it1) }
            binding.chatRoomDetailArmsImage.setImageResource(
                when(viewModel.userArms.value){
                    1 -> R.drawable.ic_arms_knife
                    2 -> R.drawable.ic_arms_bigknife
                    3 -> R.drawable.ic_arms_bow
                    4 -> R.drawable.ic_arms_gunax
                    5 -> R.drawable.ic_arms_lightcrossbow
                    6 -> R.drawable.ic_arms_double_knife
                    7 -> R.drawable.ic_arms_stick
                    8 -> R.drawable.ic_arms_hcrossbow
                    9 -> R.drawable.ic_arms_bighammer
                    10 -> R.drawable.ic_arms_gunspear
                    11 -> R.drawable.ic_arms_singo_knife
                    12 -> R.drawable.ic_arms_spear
                    13 -> R.drawable.ic_arms_ax
                    14 -> R.drawable.ic_arms_flout
                    else -> R.drawable.spear
                }
            )
        })

        val arms = arrayListOf<String>("皆可",
            "太刀", "大劍",
            "弓箭", "充能斧",
            "輕弩", "雙劍",
            "操蟲棍", "重弩",
            "大錘", "銃槍", "單手劍",
            "長槍", "斬擊斧", "狩獵笛"
        )
        val adapterArmsType = ArrayAdapter(
            MonsterApplication.instance,
            R.layout.support_simple_spinner_dropdown_item,
            arms
        )

        binding.chatRoomDetailArmsSpinner.adapter = adapterArmsType

        binding.chatRoomDetailArmsSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (id) {
                        0L -> {
                            viewModel.userArmsType.value?.armsType = "皆可"
                            viewModel.userArms.value = 0
                        }
                        1L -> {
                            viewModel.userArmsType.value?.armsType  = "太刀"
                            viewModel.userArms.value = 1
                        }
                        2L -> {
                            viewModel.userArmsType.value?.armsType = "大劍"
                            viewModel.userArms.value = 2
                        }
                        3L -> {
                            viewModel.userArmsType.value?.armsType  = "弓箭"
                            viewModel.userArms.value = 3
                        }
                        4L -> {
                            viewModel.userArmsType.value?.armsType  = "充能斧"
                            viewModel.userArms.value = 4
                        }
                        5L -> {
                            viewModel.userArmsType.value?.armsType  = "輕弩"
                            viewModel.userArms.value = 5
                        }
                        6L -> {
                            viewModel.userArmsType.value?.armsType  = "雙劍"
                            viewModel.userArms.value = 6
                        }
                        7L -> {
                            viewModel.userArmsType.value?.armsType  = "操蟲棍"
                            viewModel.userArms.value = 7
                        }
                        8L -> {
                            viewModel.userArmsType.value?.armsType  = "重弩"
                            viewModel.userArms.value = 8
                        }
                        9L -> {
                            viewModel.userArmsType.value?.armsType  = "大錘"
                            viewModel.userArms.value = 9
                        }
                        10L -> {
                            viewModel.userArmsType.value?.armsType  = "銃槍"
                            viewModel.userArms.value = 10
                        }
                        11L -> {
                            viewModel.userArmsType.value?.armsType  = "單手劍"
                            viewModel.userArms.value = 11
                        }
                        12L -> {
                            viewModel.userArmsType.value?.armsType  = "長槍"
                            viewModel.userArms.value = 12
                        }
                        13L -> {
                            viewModel.userArmsType.value?.armsType  = "斬擊斧"
                            viewModel.userArms.value = 13
                        }
                        14L -> {
                            viewModel.userArmsType.value?.armsType  = "狩獵笛"
                            viewModel.userArms.value = 14
                        }

                    }
                }
            }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).hiddingBottomnav()
        (activity as MainActivity).hiddingToolbar()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).getBottomnav()
        (activity as MainActivity).getToolbar()
    }
}
