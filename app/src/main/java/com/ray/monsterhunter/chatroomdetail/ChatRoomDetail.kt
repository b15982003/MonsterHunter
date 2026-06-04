package com.ray.monsterhunter.chatroomdetail


import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ray.monsterhunter.MainActivity
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.NavigationDirections
import com.ray.monsterhunter.R
import com.ray.monsterhunter.databinding.ChatRoomDetailFragmentBinding
import com.ray.monsterhunter.ext.getVmFactory
import com.ray.monsterhunter.util.UserManager

class ChatRoomDetail : Fragment() {
    private val viewModel by viewModels<ChatRoomDetailViewModel> {
        getVmFactory(
            ChatRoomDetailArgs.fromBundle(
                requireArguments()
            ).chatRoom
        )
    }
    lateinit var binding: ChatRoomDetailFragmentBinding
    private var previousStartTime: String? = null

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adapter = ChatRoomDetailAdapter(ChatRoomDetailAdapter.OnClickListener {
        }, viewModel)

        //初始化
        viewModel.createLanguageTTS()
        binding = ChatRoomDetailFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = bars.top, bottom = bars.bottom)
            insets
        }

        binding.chatRoomDetailTimeBg.visibility = View.GONE
        binding.chatRoomDetailTimeStart.visibility = View.GONE
        binding.chatRoomDetailTimeEnd.visibility = View.GONE

        binding.chatRoomDetailTextMessageRecy.adapter = adapter
        binding.chatRoomDetailArmsImage.setImageResource(R.drawable.ic_arms_spear)

        binding.chatRoomDetailSentMessage.setOnClickListener() {
            viewModel.message.value?.let { it1 -> viewModel.sentMessage(it1) }
            Handler().postDelayed({
                binding.chatRoomDetailItemEditText.text.clear()
            }, 500)
        }

        binding.chatRoomDetailToolbarBack.setOnClickListener {
            leaveRoom()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    leaveRoom()
                }
            },
        )

        viewModel.emptySeat.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                viewModel.teammateList = viewModel.chatRoom.value?.teammate!!
                viewModel.teammateList.add(UserManager.userData.email.toString())
                viewModel.emptySeat.value = false
            }
        })

        viewModel.leave.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Toast.makeText(MonsterApplication.instance, "人員已經滿了", Toast.LENGTH_LONG)
                    .show()
                findNavController().navigateUp()
                viewModel.getOutFinish()
            }
        })

        viewModel.isGoon.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                viewModel.teammateList.remove(UserManager.userData.email.toString())
            }
        })

        viewModel.timeSec.observe(viewLifecycleOwner, Observer {
            binding.chatRoomDetailSecNumber.text = (viewModel.timeCheck % 10).toString()
            binding.chatRoomDetailTenSecNumber.text = (if (viewModel.timeCheck / 10 > 5) {
                0
            } else {
                viewModel.timeCheck / 10
            }).toString()
            binding.chatRoomDetailMinNumber.text = (viewModel.timeCheck / 60).toString()
            binding.chatRoomDetailTenMinNumber.text = (viewModel.timeCheck / 600).toString()
        })

        viewModel.liveChatRoom.observe(viewLifecycleOwner, Observer {
            it?.let {
                Handler().postDelayed({
                    if (viewModel.liveChatRoom.value?.endToScore == "true") {

                        viewModel.isGoon.value = false
                        viewModel.endSpeakerReady()
                        findNavController().navigate(
                            NavigationDirections.actionGlobalChatRoomDetailScore(
                                viewModel.chatRoom.value!!
                            )
                        )
                    }
                }, 1000)

                val currentStartTime = viewModel.liveChatRoom.value?.startTime
                val notOwner = viewModel.chatRoom.value?.userId != UserManager.userData.id
                val notFinished = viewModel.liveChatRoom.value?.endToScore == "false"
                val isFirstEmission = previousStartTime == null
                val startedTransition = previousStartTime != "true" && currentStartTime == "true"
                val endedTransition = previousStartTime == "true" && currentStartTime == "false"

                if (notOwner && notFinished && startedTransition) {
                    binding.chatRoomDetailTimeBg.visibility = View.VISIBLE
                    binding.chatRoomDetailTimeStart.visibility = View.VISIBLE
                    binding.chatRoomDetailTimeEnd.visibility = View.GONE
                    Handler().postDelayed({
                        binding.chatRoomDetailTimeBg.visibility = View.GONE
                        binding.chatRoomDetailTimeStart.visibility = View.GONE
                    }, 1000)
                } else if (notOwner && notFinished && endedTransition) {
                    binding.chatRoomDetailTimeBg.visibility = View.VISIBLE
                    binding.chatRoomDetailTimeStart.visibility = View.GONE
                    binding.chatRoomDetailTimeEnd.visibility = View.VISIBLE

                    Handler().postDelayed({
                        binding.chatRoomDetailTimeBg.visibility = View.GONE
                        binding.chatRoomDetailTimeEnd.visibility = View.GONE
                    }, 1000)
                }
                previousStartTime = currentStartTime

                if (viewModel.speakerReady.value == true) {
                    if (viewModel.liveChatRoom.value?.speaker != "null") {
                        viewModel.speakerDoing()
                        when (viewModel.liveChatRoom.value?.speaker) {
                            "back" -> viewModel.say("退退退退退退退退")
                            "hit" -> viewModel.say("打他頭，打他的頭，朝頭打下去")
                            "mackUp" -> viewModel.say("補血補血補血補血")
                            "wait" -> viewModel.say("等等等等等等等等")
                        }
                    } else {
                        viewModel.speakerFree()
                    }
                }
            }
        })

        viewModel.userArms.observe(viewLifecycleOwner, Observer {
            viewModel.userArmsType.value?.let { it1 -> viewModel.getUserArms(it1) }
            binding.chatRoomDetailArmsImage.setImageResource(
                when (viewModel.userArms.value) {
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

        val arms = arrayListOf<String>(
            "皆可",
            "太刀", "大劍",
            "弓箭", "充能斧",
            "輕弩", "雙劍",
            "操蟲棍", "重弩",
            "大錘", "銃槍", "單手劍",
            "長槍", "斬擊斧", "狩獵笛"
        )
        val adapterArmsType = ArrayAdapter(
            MonsterApplication.instance,
            android.R.layout.simple_spinner_dropdown_item,
            arms
        )

        binding.chatRoomDetailArmsSpinner.adapter = adapterArmsType

        binding.chatRoomDetailArmsSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

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
                            viewModel.userArmsType.value?.armsType = "太刀"
                            viewModel.userArms.value = 1
                        }

                        2L -> {
                            viewModel.userArmsType.value?.armsType = "大劍"
                            viewModel.userArms.value = 2
                        }

                        3L -> {
                            viewModel.userArmsType.value?.armsType = "弓箭"
                            viewModel.userArms.value = 3
                        }

                        4L -> {
                            viewModel.userArmsType.value?.armsType = "充能斧"
                            viewModel.userArms.value = 4
                        }

                        5L -> {
                            viewModel.userArmsType.value?.armsType = "輕弩"
                            viewModel.userArms.value = 5
                        }

                        6L -> {
                            viewModel.userArmsType.value?.armsType = "雙劍"
                            viewModel.userArms.value = 6
                        }

                        7L -> {
                            viewModel.userArmsType.value?.armsType = "操蟲棍"
                            viewModel.userArms.value = 7
                        }

                        8L -> {
                            viewModel.userArmsType.value?.armsType = "重弩"
                            viewModel.userArms.value = 8
                        }

                        9L -> {
                            viewModel.userArmsType.value?.armsType = "大錘"
                            viewModel.userArms.value = 9
                        }

                        10L -> {
                            viewModel.userArmsType.value?.armsType = "銃槍"
                            viewModel.userArms.value = 10
                        }

                        11L -> {
                            viewModel.userArmsType.value?.armsType = "單手劍"
                            viewModel.userArms.value = 11
                        }

                        12L -> {
                            viewModel.userArmsType.value?.armsType = "長槍"
                            viewModel.userArms.value = 12
                        }

                        13L -> {
                            viewModel.userArmsType.value?.armsType = "斬擊斧"
                            viewModel.userArms.value = 13
                        }

                        14L -> {
                            viewModel.userArmsType.value?.armsType = "狩獵笛"
                            viewModel.userArms.value = 14
                        }
                    }
                }
            }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).hidingBottommost()
        (activity as MainActivity).hidingToolbar()
    }

    private var hasLeft = false

    private fun leaveRoom() {
        if (hasLeft) return
        hasLeft = true
        viewModel.outLeave()
        viewModel.userArmsType.value?.let { viewModel.cancelUser(it) }
        Handler().postDelayed({
            if (isAdded) findNavController().navigateUp()
        }, 500)
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).getBottommost()
        (activity as MainActivity).getToolbar()
        viewModel.cancelTts()
    }
}
