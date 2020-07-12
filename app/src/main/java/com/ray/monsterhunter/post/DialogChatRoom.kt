package com.ray.monsterhunter.post


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.R
import com.ray.monsterhunter.databinding.DialogChatRoomFragmentBinding
import com.ray.monsterhunter.ext.getVmFactory
import com.ray.monsterhunter.util.Logger

class DialogChatRoom : AppCompatDialogFragment() {


    private  val viewModel by viewModels<DialogChatRoomViewModel> { getVmFactory() }
    private lateinit var binding : DialogChatRoomFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogPost)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DialogChatRoomFragmentBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel



        viewModel.postMonster.observe(viewLifecycleOwner, Observer {
            it?.let {
                Logger.d(viewModel.postMonster.value.toString())

                binding.chatRoomPostImage.setImageResource(
                    when(viewModel.postMonster.value){
                        1 -> R.drawable.ic_monster
                        2 -> R.drawable.ic_add
                        3 -> R.drawable.ic_awaiting_payment
                        4 -> R.drawable.ic_address
                        5 -> R.drawable.ic_awaiting_shipment
                        6 -> R.drawable.ic_success
                        else -> R.drawable.ic_comment_black_24dp
                    }
                )
            }
        })

        val missionType = arrayListOf("選擇任務類型", "任務", "自由", "調查", "活動", "限時活動", "採集")
        val monsterName = arrayListOf("選擇魔物類型", "滅盡龍", "煌黑龍", "麒麟", "火龍", "冰牙龍", "冰呪龍")

        val adapterActivityType = ArrayAdapter(
            MonsterApplication.instance,
            android.R.layout.simple_spinner_dropdown_item,
            missionType
        )
        val adapterMonsterName = ArrayAdapter(
            MonsterApplication.instance,
            android.R.layout.simple_spinner_dropdown_item,
            monsterName)

        binding.chatRoomListTypeNameTextSpin.adapter = adapterActivityType
        binding.chatRoomListMonsterNameTextSpin.adapter = adapterMonsterName

        binding.chatRoomListTypeNameTextSpin.onItemSelectedListener =
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
                        0L -> viewModel.even.value?.typeName  = "出擊"
                        1L -> viewModel.even.value?.typeName  = "任務"
                        2L -> viewModel.even.value?.typeName  = "自由"
                        3L -> viewModel.even.value?.typeName  = "調查"
                        4L -> viewModel.even.value?.typeName  = "活動"
                        5L -> viewModel.even.value?.typeName  = "限時活動"
                        5L -> viewModel.even.value?.typeName  = "採集"
                    }
                }
            }


        binding.chatRoomListMonsterNameTextSpin.onItemSelectedListener =
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
                            viewModel.even.value?.image = "隨機攻打生物"
                            viewModel.postMonster.value = 0
                        }
                        1L -> {
                            viewModel.even.value?.image = "滅盡龍"
                            viewModel.postMonster.value = 1
                        }
                        2L -> {
                            viewModel.even.value?.image = "煌黑龍"
                            viewModel.postMonster.value = 2
                        }
                        3L -> {
                            viewModel.even.value?.image = "麒麟"
                            viewModel.postMonster.value = 3
                        }
                        4L -> {
                            viewModel.even.value?.image = "火龍"
                            viewModel.postMonster.value = 4
                        }
                        5L -> {
                            viewModel.even.value?.image = "冰牙龍"
                            viewModel.postMonster.value = 5
                        }
                        6L -> {
                            viewModel.even.value?.image = "冰呪龍"
                            viewModel.postMonster.value = 6
                        }
                    }
                }
            }

        return binding.root
    }


}
