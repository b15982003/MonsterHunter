package com.ray.monsterhunter.chatroomScore


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ray.monsterhunter.data.ArmsType
import com.ray.monsterhunter.data.User
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
            viewModel.user1.value?.armsType?.let { it1 -> viewModel.goPlus(it1,viewModel.liveUserOne) }

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
            viewModel.user1.value?.armsType?.let { it1 -> viewModel.goMinus(it1,viewModel.liveUserOne) }

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
            viewModel.user2.value?.armsType?.let { it1 -> viewModel.goPlus(it1,viewModel.liveUserTwo) }


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
            viewModel.user2.value?.armsType?.let { it1 -> viewModel.goMinus(it1,viewModel.liveUserTwo) }


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
            viewModel.user3.value?.armsType?.let { it1 -> viewModel.goPlus(it1,viewModel.liveUserThree) }


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
            viewModel.user3.value?.armsType?.let { it1 -> viewModel.goMinus(it1,viewModel.liveUserThree) }


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
            viewModel.user4.value?.armsType?.let { it1 -> viewModel.goPlus(it1,viewModel.liveUserFour) }

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
            viewModel.user4.value?.armsType?.let { it1 -> viewModel.goMinus(it1, viewModel.liveUserFour) }


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
