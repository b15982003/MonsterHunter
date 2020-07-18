package com.ray.monsterhunter.chattoomdetail


import android.R
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.HandlerCompat.postDelayed
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ray.monsterhunter.MainActivity
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.data.ChatRoom
import com.ray.monsterhunter.databinding.ChatRoomDetailFragmentBinding
import com.ray.monsterhunter.ext.getVmFactory
import com.ray.monsterhunter.util.Logger
import com.ray.monsterhunter.util.UserManager


class ChatRoomDetail : Fragment() {


    private val viewModel by viewModels<ChatRoomDetailViewModel> { getVmFactory(ChatRoomDetailArgs.fromBundle(requireArguments()).chatRoom) }
    lateinit var binding : ChatRoomDetailFragmentBinding


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
        binding.chatRoomDetailTextMessageRecy.adapter = ChatRoomDetailAdapter(ChatRoomDetailAdapter.OnClickListener{
        })

        binding.chatRoomDetailReadyButton.setOnClickListener(){
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
        }

        binding.chatRoomDetailStartButton.setOnClickListener(){
            viewModel.startTimming()
            binding.chatRoomDetailStartButton.visibility = View.GONE
            binding.chatRoomDetailEndButton.visibility = View.VISIBLE

        }

        binding.chatRoomDetailEndButton.setOnClickListener(){
            viewModel.endTimming()
            binding.chatRoomDetailStartButton.visibility = View.VISIBLE
            binding.chatRoomDetailEndButton.visibility = View.GONE

        }



        binding.chatRoomDetailSentMessage.setOnClickListener(){
            viewModel.message.value?.let { it1 -> viewModel.sentMessage(it1) }
            Handler().postDelayed({
                binding.chatRoomDetailItemEditText.text.clear()
            },500)
        }

        binding.chatRoomDetailToolbarBack.setOnClickListener(){
            viewModel.outLeave()
            findNavController().navigateUp()
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
            R.layout.simple_spinner_dropdown_item,
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
                        0L -> viewModel.message.value?.userArmsType = "皆可"
                        1L -> viewModel.message.value?.userArmsType = "太刀"
                        2L -> viewModel.message.value?.userArmsType = "大劍"
                        3L -> viewModel.message.value?.userArmsType = "弓箭"
                        4L -> viewModel.message.value?.userArmsType = "充能斧"
                        5L -> viewModel.message.value?.userArmsType = "輕弩"
                        6L -> viewModel.message.value?.userArmsType = "雙劍"
                        7L -> viewModel.message.value?.userArmsType = "操蟲棍"
                        8L -> viewModel.message.value?.userArmsType = "重弩"
                        9L -> viewModel.message.value?.userArmsType = "大錘"
                        10L -> viewModel.message.value?.userArmsType = "銃槍"
                        11L -> viewModel.message.value?.userArmsType = "單手劍"
                        12L -> viewModel.message.value?.userArmsType = "長槍"
                        13L -> viewModel.message.value?.userArmsType = "斬擊斧"
                        14L -> viewModel.message.value?.userArmsType = "狩獵笛"

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
