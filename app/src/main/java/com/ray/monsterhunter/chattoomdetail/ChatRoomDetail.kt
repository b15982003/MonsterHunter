package com.ray.monsterhunter.chattoomdetail


import android.os.Bundle
import android.os.Handler
import android.view.*
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
        binding.chatRoomDetailTextMessageRecy.adapter = ChatRoomDetailAdapter(ChatRoomDetailAdapter.OnClickListener{
        })

        binding.chatRoomDetailReadyButton.setOnClickListener(){
            if (viewModel.ready.value == false){
                viewModel.getready()
                binding.chatRoomDetailStartButton.visibility = View.VISIBLE
                binding.chatRoomDetailEndButton.visibility = View.VISIBLE
                binding.chatRoomDetailStartBackground.visibility = View.VISIBLE
            }else{
                viewModel.endreadt()
                binding.chatRoomDetailStartButton.visibility = View.GONE
                binding.chatRoomDetailEndButton.visibility = View.GONE
                binding.chatRoomDetailStartBackground.visibility = View.GONE
            }
        }

        binding.chatRoomDetailStartButton.setOnClickListener(){
            viewModel.startTimming()
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
