package com.ray.monsterhunter.chatroomScore


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ray.monsterhunter.data.ArmsType
import com.ray.monsterhunter.databinding.ChatRoomDetailScoreFragmentBinding
import com.ray.monsterhunter.ext.getVmFactory
import com.ray.monsterhunter.util.Logger
import com.ray.monsterhunter.util.UserManager


class ChatRoomDetailScore : Fragment() {


    private val viewModel by viewModels<ChatRoomDetailScoreViewModel> {
        getVmFactory(
            ChatRoomDetailScoreArgs.fromBundle(
                requireArguments()
            ).chatRoomDetail
        )
    }
    lateinit var binding: ChatRoomDetailScoreFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Logger.d("ChatRoomDetailScore.onCreateView")
        Logger.d("ChatRoomDetailScore.arg=${ChatRoomDetailScoreArgs.fromBundle(
            requireArguments()
        ).chatRoomDetail}")

        binding = ChatRoomDetailScoreFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chatRoomScoreSentButton.setOnClickListener() {

            binding.chatRoomScoreBgImage.playAnimation()
            binding.chatRoomScoreBg.visibility = View.VISIBLE
            binding.chatRoomScoreBgImage.visibility = View.VISIBLE

            if (isRoomOwner()) {
                viewModel.chatRoom.value?.endToScore = "false"
                viewModel.updateChatRoomInfo()
            }

            Handler().postDelayed({
                findNavController().navigateUp()
                binding.chatRoomScoreBgImage.cancelAnimation()
            }, 2000)

        }

        binding.chatRoomScoreUser1PlusNo.visibility = View.GONE
        binding.chatRoomScoreUser1LessNo.visibility = View.GONE
        binding.chatRoomScoreBg.visibility = View.GONE
        binding.chatRoomScoreBgImage.visibility = View.GONE

        viewModel.liveChatRoom.observe(viewLifecycleOwner, Observer {
            Logger.d("viewModel.liveChatRoom.observe, it=$it")
            it?.let {
                if (it.missionResult == "true") {
                    binding.chatRoomScoreSuccessImage.visibility = View.VISIBLE
                    binding.chatRoomScoreFailedImage.visibility = View.INVISIBLE
                } else {
                    binding.chatRoomScoreSuccessImage.visibility = View.INVISIBLE
                    binding.chatRoomScoreFailedImage.visibility = View.VISIBLE
                }
            }
        })

        viewModel.isDone.observe(viewLifecycleOwner, Observer {
            Logger.d("viewModel.isDone.observe, it=$it")
            it?.let {
                if (isRoomOwner()) {
                    if (it) {
                        Logger.d("user Done ${viewModel.history.value}")
                        viewModel.pushHistory1()
                        viewModel.pushHistory2()
                        viewModel.pushHistory3()
                        viewModel.pushHistory4()

                    }
                }
                viewModel.isDone.value = null
            }
        })

        viewModel.history.observe(viewLifecycleOwner, Observer {
            Logger.d("viewModel.history.observe, it=$it")

            if (it.user1 != "" && it.user2 != "" && it.user3 != "" && it.user4 != "") {

                Logger.d("all user not empty")

                viewModel.isDone.value = true
            }
        })



        viewModel.user1.observe(viewLifecycleOwner, Observer {
            Logger.d("viewModel.user1.observe, it=$it")
            it?.let {
                if (viewModel.user1.value?.createTime != null) {
                    Logger.d("user1 in")
                    viewModel.history.value?.user1 = viewModel.user1.value?.userId
                    viewModel.history.value?.user1Type = viewModel.user1.value?.armsType.toString()
                    viewModel.history.value = viewModel.history.value
                }
            }
        })

        viewModel.user2.observe(viewLifecycleOwner, Observer {
            Logger.d("viewModel.user2.observe, it=$it")
            it?.let {
                if (viewModel.user2.value?.createTime != null) {
                    Logger.d("user2 in")
                    viewModel.history.value?.user2 = viewModel.user2.value?.userId
                    viewModel.history.value?.user2Type = viewModel.user2.value?.armsType.toString()
                    viewModel.history.value = viewModel.history.value
                }
            }
        })

        viewModel.user3.observe(viewLifecycleOwner, Observer {
            Logger.d("viewModel.user3.observe, it=$it")
            it?.let {
                if (viewModel.user3.value?.createTime != null) {
                    Logger.d("user3 in")
                    viewModel.history.value?.user3 = viewModel.user3.value?.userId
                    viewModel.history.value?.user3Type = viewModel.user3.value?.armsType.toString()
                    viewModel.history.value = viewModel.history.value
                }

            }
        })

        viewModel.user4.observe(viewLifecycleOwner, Observer {
            Logger.d("viewModel.user4.observe, it=$it")
            it?.let {
                if (viewModel.user4.value?.createTime != null) {
                    Logger.d("user4 in")
                    viewModel.history.value?.user4 = viewModel.user4.value?.userId
                    viewModel.history.value?.user4Type = viewModel.user4.value?.armsType.toString()
                    viewModel.history.value = viewModel.history.value
                }
            }
        })

        binding.chatRoomScoreUser1Plus.setOnClickListener() {
            when (viewModel.user1.value?.armsType) {
                "太刀" -> viewModel.liveUserOne.value?.armsType?.A =
                    viewModel.liveUserOne.value?.armsType?.A?.plus(1)
                "大劍" -> viewModel.liveUserOne.value?.armsType?.B =
                    viewModel.liveUserOne.value?.armsType?.B?.plus(1)
                "弓箭" -> viewModel.liveUserOne.value?.armsType?.C =
                    viewModel.liveUserOne.value?.armsType?.C?.plus(1)
                "充能斧" -> viewModel.liveUserOne.value?.armsType?.D =
                    viewModel.liveUserOne.value?.armsType?.D?.plus(1)
                "輕弩" -> viewModel.liveUserOne.value?.armsType?.E =
                    viewModel.liveUserOne.value?.armsType?.E?.plus(1)
                "雙劍" -> viewModel.liveUserOne.value?.armsType?.F =
                    viewModel.liveUserOne.value?.armsType?.F?.plus(1)
                "操蟲棍" -> viewModel.liveUserOne.value?.armsType?.G =
                    viewModel.liveUserOne.value?.armsType?.G?.plus(1)
                "重弩" -> viewModel.liveUserOne.value?.armsType?.H =
                    viewModel.liveUserOne.value?.armsType?.H?.plus(1)
                "大錘" -> viewModel.liveUserOne.value?.armsType?.I =
                    viewModel.liveUserOne.value?.armsType?.I?.plus(1)
                "銃槍" -> viewModel.liveUserOne.value?.armsType?.J =
                    viewModel.liveUserOne.value?.armsType?.J?.plus(1)
                "單手劍" -> viewModel.liveUserOne.value?.armsType?.K =
                    viewModel.liveUserOne.value?.armsType?.K?.plus(1)
                "長槍" -> viewModel.liveUserOne.value?.armsType?.L =
                    viewModel.liveUserOne.value?.armsType?.L?.plus(1)
                "斬擊斧" -> viewModel.liveUserOne.value?.armsType?.M =
                    viewModel.liveUserOne.value?.armsType?.M?.plus(1)
                "狩獵笛" -> viewModel.liveUserOne.value?.armsType?.N =
                    viewModel.liveUserOne.value?.armsType?.N?.plus(1)
                else -> "皆可"
            }
            binding.chatRoomScoreUser1Plus.visibility = View.GONE
            binding.chatRoomScoreUser1Less.visibility = View.GONE
            binding.chatRoomScoreUser1PlusNo.visibility = View.VISIBLE
            binding.chatRoomScoreUser1LessNo.visibility = View.GONE

            if (UserManager.userData.id == viewModel.chatRoom.value?.userId) {
                viewModel.liveUserOne.value?.allFight =
                    viewModel.liveUserOne.value?.allFight?.plus(1)
            }

            viewModel.updateUserOne()
        }

        binding.chatRoomScoreUser1Less.setOnClickListener() {
            when (viewModel.user1.value?.armsType) {
                "太刀" -> viewModel.liveUserOne.value?.armsType?.A =
                    viewModel.liveUserOne.value?.armsType?.A?.minus(1)
                "大劍" -> viewModel.liveUserOne.value?.armsType?.B =
                    viewModel.liveUserOne.value?.armsType?.B?.minus(1)
                "弓箭" -> viewModel.liveUserOne.value?.armsType?.C =
                    viewModel.liveUserOne.value?.armsType?.C?.minus(1)
                "充能斧" -> viewModel.liveUserOne.value?.armsType?.D =
                    viewModel.liveUserOne.value?.armsType?.D?.minus(1)
                "輕弩" -> viewModel.liveUserOne.value?.armsType?.E =
                    viewModel.liveUserOne.value?.armsType?.E?.minus(1)
                "雙劍" -> viewModel.liveUserOne.value?.armsType?.F =
                    viewModel.liveUserOne.value?.armsType?.F?.minus(1)
                "操蟲棍" -> viewModel.liveUserOne.value?.armsType?.G =
                    viewModel.liveUserOne.value?.armsType?.G?.minus(1)
                "重弩" -> viewModel.liveUserOne.value?.armsType?.H =
                    viewModel.liveUserOne.value?.armsType?.H?.minus(1)
                "大錘" -> viewModel.liveUserOne.value?.armsType?.I =
                    viewModel.liveUserOne.value?.armsType?.I?.minus(1)
                "銃槍" -> viewModel.liveUserOne.value?.armsType?.J =
                    viewModel.liveUserOne.value?.armsType?.J?.minus(1)
                "單手劍" -> viewModel.liveUserOne.value?.armsType?.K =
                    viewModel.liveUserOne.value?.armsType?.K?.minus(1)
                "長槍" -> viewModel.liveUserOne.value?.armsType?.L =
                    viewModel.liveUserOne.value?.armsType?.L?.minus(1)
                "斬擊斧" -> viewModel.liveUserOne.value?.armsType?.M =
                    viewModel.liveUserOne.value?.armsType?.M?.minus(1)
                "狩獵笛" -> viewModel.liveUserOne.value?.armsType?.N =
                    viewModel.liveUserOne.value?.armsType?.N?.minus(1)
                else -> "皆可"
            }
            binding.chatRoomScoreUser1Plus.visibility = View.GONE
            binding.chatRoomScoreUser1Less.visibility = View.GONE
            binding.chatRoomScoreUser1PlusNo.visibility = View.GONE
            binding.chatRoomScoreUser1LessNo.visibility = View.VISIBLE
            if (UserManager.userData.id == viewModel.chatRoom.value?.userId) {
                viewModel.liveUserOne.value?.allFight =
                    viewModel.liveUserOne.value?.allFight?.plus(1)
            }

            viewModel.updateUserOne()
        }

        viewModel.liveUserOne.observe(viewLifecycleOwner, Observer {
            Logger.i("viewModel.liveUserOne.observe, it=$it")

            it?.let {
                viewModel.liveUserOne.value?.armsType?.let {
                    binding.chatRoomScoreUser1Number.text = getScoreOfArms(viewModel.user1.value?.armsType ?: "", it)
                }
            }
        })

        binding.chatRoomScoreUser2Plus.setOnClickListener() {
            when (viewModel.user2.value?.armsType) {
                "太刀" -> viewModel.liveUserTwo.value?.armsType?.A =
                    viewModel.liveUserTwo.value?.armsType?.A?.plus(1)
                "大劍" -> viewModel.liveUserTwo.value?.armsType?.B =
                    viewModel.liveUserTwo.value?.armsType?.B?.plus(1)
                "弓箭" -> viewModel.liveUserTwo.value?.armsType?.C =
                    viewModel.liveUserTwo.value?.armsType?.C?.plus(1)
                "充能斧" -> viewModel.liveUserTwo.value?.armsType?.D =
                    viewModel.liveUserTwo.value?.armsType?.D?.plus(1)
                "輕弩" -> viewModel.liveUserTwo.value?.armsType?.E =
                    viewModel.liveUserTwo.value?.armsType?.E?.plus(1)
                "雙劍" -> viewModel.liveUserTwo.value?.armsType?.F =
                    viewModel.liveUserTwo.value?.armsType?.F?.plus(1)
                "操蟲棍" -> viewModel.liveUserTwo.value?.armsType?.G =
                    viewModel.liveUserTwo.value?.armsType?.G?.plus(1)
                "重弩" -> viewModel.liveUserTwo.value?.armsType?.H =
                    viewModel.liveUserTwo.value?.armsType?.H?.plus(1)
                "大錘" -> viewModel.liveUserTwo.value?.armsType?.I =
                    viewModel.liveUserTwo.value?.armsType?.I?.plus(1)
                "銃槍" -> viewModel.liveUserTwo.value?.armsType?.J =
                    viewModel.liveUserTwo.value?.armsType?.J?.plus(1)
                "單手劍" -> viewModel.liveUserTwo.value?.armsType?.K =
                    viewModel.liveUserTwo.value?.armsType?.K?.plus(1)
                "長槍" -> viewModel.liveUserTwo.value?.armsType?.L =
                    viewModel.liveUserTwo.value?.armsType?.L?.plus(1)
                "斬擊斧" -> viewModel.liveUserTwo.value?.armsType?.M =
                    viewModel.liveUserTwo.value?.armsType?.M?.plus(1)
                "狩獵笛" -> viewModel.liveUserTwo.value?.armsType?.N =
                    viewModel.liveUserTwo.value?.armsType?.N?.plus(1)
                else -> "皆可"
            }

            binding.chatRoomScoreUser2Plus.visibility = View.GONE
            binding.chatRoomScoreUser2Less.visibility = View.GONE
            binding.chatRoomScoreUser2PlusNo.visibility = View.VISIBLE
            binding.chatRoomScoreUser2LessNo.visibility = View.GONE
            if (UserManager.userData.id == viewModel.chatRoom.value?.userId) {
                viewModel.liveUserTwo.value?.allFight =
                    viewModel.liveUserTwo.value?.allFight?.plus(1)
            }
            viewModel.updateUserTwo()

        }

        binding.chatRoomScoreUser2Less.setOnClickListener() {
            when (viewModel.user2.value?.armsType) {
                "太刀" -> viewModel.liveUserTwo.value?.armsType?.A =
                    viewModel.liveUserTwo.value?.armsType?.A?.minus(1)
                "大劍" -> viewModel.liveUserTwo.value?.armsType?.B =
                    viewModel.liveUserTwo.value?.armsType?.B?.minus(1)
                "弓箭" -> viewModel.liveUserTwo.value?.armsType?.C =
                    viewModel.liveUserTwo.value?.armsType?.C?.minus(1)
                "充能斧" -> viewModel.liveUserTwo.value?.armsType?.D =
                    viewModel.liveUserTwo.value?.armsType?.D?.minus(1)
                "輕弩" -> viewModel.liveUserTwo.value?.armsType?.E =
                    viewModel.liveUserTwo.value?.armsType?.E?.minus(1)
                "雙劍" -> viewModel.liveUserTwo.value?.armsType?.F =
                    viewModel.liveUserTwo.value?.armsType?.F?.minus(1)
                "操蟲棍" -> viewModel.liveUserTwo.value?.armsType?.G =
                    viewModel.liveUserTwo.value?.armsType?.G?.minus(1)
                "重弩" -> viewModel.liveUserTwo.value?.armsType?.H =
                    viewModel.liveUserTwo.value?.armsType?.H?.minus(1)
                "大錘" -> viewModel.liveUserTwo.value?.armsType?.I =
                    viewModel.liveUserTwo.value?.armsType?.I?.minus(1)
                "銃槍" -> viewModel.liveUserTwo.value?.armsType?.J =
                    viewModel.liveUserTwo.value?.armsType?.J?.minus(1)
                "單手劍" -> viewModel.liveUserTwo.value?.armsType?.K =
                    viewModel.liveUserTwo.value?.armsType?.K?.minus(1)
                "長槍" -> viewModel.liveUserTwo.value?.armsType?.L =
                    viewModel.liveUserTwo.value?.armsType?.L?.minus(1)
                "斬擊斧" -> viewModel.liveUserTwo.value?.armsType?.M =
                    viewModel.liveUserTwo.value?.armsType?.M?.minus(1)
                "狩獵笛" -> viewModel.liveUserTwo.value?.armsType?.N =
                    viewModel.liveUserTwo.value?.armsType?.N?.minus(1)
                else -> "皆可"
            }

            binding.chatRoomScoreUser2Plus.visibility = View.GONE
            binding.chatRoomScoreUser2Less.visibility = View.GONE
            binding.chatRoomScoreUser2PlusNo.visibility = View.GONE
            binding.chatRoomScoreUser2LessNo.visibility = View.VISIBLE
            if (UserManager.userData.id == viewModel.chatRoom.value?.userId) {
                viewModel.liveUserTwo.value?.allFight =
                    viewModel.liveUserTwo.value?.allFight?.plus(1)
            }
            viewModel.updateUserTwo()
        }

        viewModel.liveUserTwo.observe(viewLifecycleOwner, Observer {
            Logger.i("viewModel.liveUserTwo.observe, it=$it")

            it?.let {
                viewModel.liveUserTwo.value?.armsType?.let {
                    binding.chatRoomScoreUser2Number.text = getScoreOfArms(viewModel.user2.value?.armsType ?: "", it)
                }
            }
        })

        binding.chatRoomScoreUser3Plus.setOnClickListener() {
            when (viewModel.user3.value?.armsType) {
                "太刀" -> viewModel.liveUserThree.value?.armsType?.A =
                    viewModel.liveUserThree.value?.armsType?.A?.plus(1)
                "大劍" -> viewModel.liveUserThree.value?.armsType?.B =
                    viewModel.liveUserThree.value?.armsType?.B?.plus(1)
                "弓箭" -> viewModel.liveUserThree.value?.armsType?.C =
                    viewModel.liveUserThree.value?.armsType?.C?.plus(1)
                "充能斧" -> viewModel.liveUserThree.value?.armsType?.D =
                    viewModel.liveUserThree.value?.armsType?.D?.plus(1)
                "輕弩" -> viewModel.liveUserThree.value?.armsType?.E =
                    viewModel.liveUserThree.value?.armsType?.E?.plus(1)
                "雙劍" -> viewModel.liveUserThree.value?.armsType?.F =
                    viewModel.liveUserThree.value?.armsType?.F?.plus(1)
                "操蟲棍" -> viewModel.liveUserThree.value?.armsType?.G =
                    viewModel.liveUserThree.value?.armsType?.G?.plus(1)
                "重弩" -> viewModel.liveUserThree.value?.armsType?.H =
                    viewModel.liveUserThree.value?.armsType?.H?.plus(1)
                "大錘" -> viewModel.liveUserThree.value?.armsType?.I =
                    viewModel.liveUserThree.value?.armsType?.I?.plus(1)
                "銃槍" -> viewModel.liveUserThree.value?.armsType?.J =
                    viewModel.liveUserThree.value?.armsType?.J?.plus(1)
                "單手劍" -> viewModel.liveUserThree.value?.armsType?.K =
                    viewModel.liveUserThree.value?.armsType?.K?.plus(1)
                "長槍" -> viewModel.liveUserThree.value?.armsType?.L =
                    viewModel.liveUserThree.value?.armsType?.L?.plus(1)
                "斬擊斧" -> viewModel.liveUserThree.value?.armsType?.M =
                    viewModel.liveUserThree.value?.armsType?.M?.plus(1)
                "狩獵笛" -> viewModel.liveUserThree.value?.armsType?.N =
                    viewModel.liveUserThree.value?.armsType?.N?.plus(1)
                else -> "皆可"
            }

            binding.chatRoomScoreUser3Plus.visibility = View.GONE
            binding.chatRoomScoreUser3Less.visibility = View.GONE
            binding.chatRoomScoreUser3PlusNo.visibility = View.VISIBLE
            binding.chatRoomScoreUser3LessNo.visibility = View.GONE
            if (UserManager.userData.id == viewModel.chatRoom.value?.userId) {
                viewModel.liveUserThree.value?.allFight =
                    viewModel.liveUserThree.value?.allFight?.plus(1)
            }
            viewModel.updateUserThree()
        }

        binding.chatRoomScoreUser3Less.setOnClickListener() {
            when (viewModel.user3.value?.armsType) {
                "太刀" -> viewModel.liveUserThree.value?.armsType?.A =
                    viewModel.liveUserThree.value?.armsType?.A?.minus(1)
                "大劍" -> viewModel.liveUserThree.value?.armsType?.B =
                    viewModel.liveUserThree.value?.armsType?.B?.minus(1)
                "弓箭" -> viewModel.liveUserThree.value?.armsType?.C =
                    viewModel.liveUserThree.value?.armsType?.C?.minus(1)
                "充能斧" -> viewModel.liveUserThree.value?.armsType?.D =
                    viewModel.liveUserThree.value?.armsType?.D?.minus(1)
                "輕弩" -> viewModel.liveUserThree.value?.armsType?.E =
                    viewModel.liveUserThree.value?.armsType?.E?.minus(1)
                "雙劍" -> viewModel.liveUserThree.value?.armsType?.F =
                    viewModel.liveUserThree.value?.armsType?.F?.minus(1)
                "操蟲棍" -> viewModel.liveUserThree.value?.armsType?.G =
                    viewModel.liveUserThree.value?.armsType?.G?.minus(1)
                "重弩" -> viewModel.liveUserThree.value?.armsType?.H =
                    viewModel.liveUserThree.value?.armsType?.H?.minus(1)
                "大錘" -> viewModel.liveUserThree.value?.armsType?.I =
                    viewModel.liveUserThree.value?.armsType?.I?.minus(1)
                "銃槍" -> viewModel.liveUserThree.value?.armsType?.J =
                    viewModel.liveUserThree.value?.armsType?.J?.minus(1)
                "單手劍" -> viewModel.liveUserThree.value?.armsType?.K =
                    viewModel.liveUserThree.value?.armsType?.K?.minus(1)
                "長槍" -> viewModel.liveUserThree.value?.armsType?.L =
                    viewModel.liveUserThree.value?.armsType?.L?.minus(1)
                "斬擊斧" -> viewModel.liveUserThree.value?.armsType?.M =
                    viewModel.liveUserThree.value?.armsType?.M?.minus(1)
                "狩獵笛" -> viewModel.liveUserThree.value?.armsType?.N =
                    viewModel.liveUserThree.value?.armsType?.N?.minus(1)
                else -> "皆可"
            }

            binding.chatRoomScoreUser3Plus.visibility = View.GONE
            binding.chatRoomScoreUser3Less.visibility = View.GONE
            binding.chatRoomScoreUser3PlusNo.visibility = View.GONE
            binding.chatRoomScoreUser3LessNo.visibility = View.VISIBLE
            if (UserManager.userData.id == viewModel.chatRoom.value?.userId) {
                viewModel.liveUserThree.value?.allFight =
                    viewModel.liveUserThree.value?.allFight?.plus(1)
            }
            viewModel.updateUserThree()
        }

        viewModel.liveUserThree.observe(viewLifecycleOwner, Observer {
            Logger.i("viewModel.liveUserThree.observe, it=$it")

            it?.let {
                viewModel.liveUserThree.value?.armsType?.let {
                    binding.chatRoomScoreUser3Number.text = getScoreOfArms(viewModel.user3.value?.armsType ?: "", it)
                }
            }
        })

        binding.chatRoomScoreUser4Plus.setOnClickListener() {
            when (viewModel.user4.value?.armsType) {
                "太刀" -> viewModel.liveUserFour.value?.armsType?.A =
                    viewModel.liveUserFour.value?.armsType?.A?.plus(1)
                "大劍" -> viewModel.liveUserFour.value?.armsType?.B =
                    viewModel.liveUserFour.value?.armsType?.B?.plus(1)
                "弓箭" -> viewModel.liveUserFour.value?.armsType?.C =
                    viewModel.liveUserFour.value?.armsType?.C?.plus(1)
                "充能斧" -> viewModel.liveUserFour.value?.armsType?.D =
                    viewModel.liveUserFour.value?.armsType?.D?.plus(1)
                "輕弩" -> viewModel.liveUserFour.value?.armsType?.E =
                    viewModel.liveUserFour.value?.armsType?.E?.plus(1)
                "雙劍" -> viewModel.liveUserFour.value?.armsType?.F =
                    viewModel.liveUserFour.value?.armsType?.F?.plus(1)
                "操蟲棍" -> viewModel.liveUserFour.value?.armsType?.G =
                    viewModel.liveUserFour.value?.armsType?.G?.plus(1)
                "重弩" -> viewModel.liveUserFour.value?.armsType?.H =
                    viewModel.liveUserFour.value?.armsType?.H?.plus(1)
                "大錘" -> viewModel.liveUserFour.value?.armsType?.I =
                    viewModel.liveUserFour.value?.armsType?.I?.plus(1)
                "銃槍" -> viewModel.liveUserFour.value?.armsType?.J =
                    viewModel.liveUserFour.value?.armsType?.J?.plus(1)
                "單手劍" -> viewModel.liveUserFour.value?.armsType?.K =
                    viewModel.liveUserFour.value?.armsType?.K?.plus(1)
                "長槍" -> viewModel.liveUserFour.value?.armsType?.L =
                    viewModel.liveUserFour.value?.armsType?.L?.plus(1)
                "斬擊斧" -> viewModel.liveUserFour.value?.armsType?.M =
                    viewModel.liveUserFour.value?.armsType?.M?.plus(1)
                "狩獵笛" -> viewModel.liveUserFour.value?.armsType?.N =
                    viewModel.liveUserFour.value?.armsType?.N?.plus(1)
                else -> "皆可"
            }
            binding.chatRoomScoreUser4Plus.visibility = View.GONE
            binding.chatRoomScoreUser4Less.visibility = View.GONE
            binding.chatRoomScoreUser4PlusNo.visibility = View.VISIBLE
            binding.chatRoomScoreUser4LessNo.visibility = View.GONE
            if (UserManager.userData.id == viewModel.chatRoom.value?.userId) {
                viewModel.liveUserFour.value?.allFight =
                    viewModel.liveUserFour.value?.allFight?.plus(1)
            }
            viewModel.updateUserFour()
        }

        binding.chatRoomScoreUser4Less.setOnClickListener() {
            when (viewModel.user4.value?.armsType) {
                "太刀" -> viewModel.liveUserFour.value?.armsType?.A =
                    viewModel.liveUserFour.value?.armsType?.A?.minus(1)
                "大劍" -> viewModel.liveUserFour.value?.armsType?.B =
                    viewModel.liveUserFour.value?.armsType?.B?.minus(1)
                "弓箭" -> viewModel.liveUserFour.value?.armsType?.C =
                    viewModel.liveUserFour.value?.armsType?.C?.minus(1)
                "充能斧" -> viewModel.liveUserFour.value?.armsType?.D =
                    viewModel.liveUserFour.value?.armsType?.D?.minus(1)
                "輕弩" -> viewModel.liveUserFour.value?.armsType?.E =
                    viewModel.liveUserFour.value?.armsType?.E?.minus(1)
                "雙劍" -> viewModel.liveUserFour.value?.armsType?.F =
                    viewModel.liveUserFour.value?.armsType?.F?.minus(1)
                "操蟲棍" -> viewModel.liveUserFour.value?.armsType?.G =
                    viewModel.liveUserFour.value?.armsType?.G?.minus(1)
                "重弩" -> viewModel.liveUserFour.value?.armsType?.H =
                    viewModel.liveUserFour.value?.armsType?.H?.minus(1)
                "大錘" -> viewModel.liveUserFour.value?.armsType?.I =
                    viewModel.liveUserFour.value?.armsType?.I?.minus(1)
                "銃槍" -> viewModel.liveUserFour.value?.armsType?.J =
                    viewModel.liveUserFour.value?.armsType?.J?.minus(1)
                "單手劍" -> viewModel.liveUserFour.value?.armsType?.K =
                    viewModel.liveUserFour.value?.armsType?.K?.minus(1)
                "長槍" -> viewModel.liveUserFour.value?.armsType?.L =
                    viewModel.liveUserFour.value?.armsType?.L?.minus(1)
                "斬擊斧" -> viewModel.liveUserFour.value?.armsType?.M =
                    viewModel.liveUserFour.value?.armsType?.M?.minus(1)
                "狩獵笛" -> viewModel.liveUserFour.value?.armsType?.N =
                    viewModel.liveUserFour.value?.armsType?.N?.minus(1)
                else -> "皆可"
            }

            binding.chatRoomScoreUser4Plus.visibility = View.GONE
            binding.chatRoomScoreUser4Less.visibility = View.GONE
            binding.chatRoomScoreUser4PlusNo.visibility = View.GONE
            binding.chatRoomScoreUser4LessNo.visibility = View.VISIBLE
            Logger.d("userId${UserManager.userData.id}")
            Logger.d("chatRoomId${viewModel.chatRoom.value?.userId}")
            if (UserManager.userData.id == viewModel.chatRoom.value?.userId) {
                viewModel.liveUserFour.value?.allFight =
                    viewModel.liveUserFour.value?.allFight?.plus(1)
                Logger.d("INNNNNNN${viewModel.liveUserFour.value?.allFight}")

            }
            viewModel.updateUserFour()
        }

        viewModel.liveUserFour.observe(viewLifecycleOwner, Observer {
            Logger.i("viewModel.liveUserFour.observe, it=$it")

            it?.let {
                viewModel.liveUserFour.value?.armsType?.let {
                    binding.chatRoomScoreUser4Number.text = getScoreOfArms(viewModel.user4.value?.armsType ?: "", it)
                }
            }
        })
    }

    fun isRoomOwner(): Boolean {
        viewModel.chatRoom.value?.let {
            if (it.userId == UserManager.userData.id) {
                return true
            }
        }
        return false
    }

    fun getScoreOfArms(armsType: String, liveUserArmsType: ArmsType): String {
        return when (armsType) {
            "太刀" -> liveUserArmsType.A.toString()
            "大劍" -> liveUserArmsType.B.toString()
            "弓箭" -> liveUserArmsType.C.toString()
            "充能斧" -> liveUserArmsType.D.toString()
            "輕弩" -> liveUserArmsType.E.toString()
            "雙劍" -> liveUserArmsType.F.toString()
            "操蟲棍" -> liveUserArmsType.G.toString()
            "重弩" -> liveUserArmsType.H.toString()
            "大錘" -> liveUserArmsType.I.toString()
            "銃槍" -> liveUserArmsType.J.toString()
            "單手劍" -> liveUserArmsType.K.toString()
            "長槍" -> liveUserArmsType.L.toString()
            "斬擊斧" -> liveUserArmsType.M.toString()
            "狩獵笛" -> liveUserArmsType.N.toString()
            else -> "請評分"
        }
    }

}