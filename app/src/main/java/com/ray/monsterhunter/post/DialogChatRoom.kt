package com.ray.monsterhunter.post


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.R
import com.ray.monsterhunter.databinding.DialogChatRoomFragmentBinding
import com.ray.monsterhunter.ext.getVmFactory
import com.ray.monsterhunter.util.UserManager
import okhttp3.internal.format
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class DialogChatRoom : AppCompatDialogFragment() {

    private val viewModel by viewModels<DialogChatRoomViewModel> { getVmFactory() }
    private lateinit var binding: DialogChatRoomFragmentBinding
    val calender = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogPost)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DialogChatRoomFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        // choose date & time
        binding.chatRoomPostDateText.setOnClickListener {
            datePicker()
        }
        binding.chatRoomPostTimeText.setOnClickListener {
            timePicker()
        }
        // change monster image
        viewModel.postMonster.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.chatRoomPostImage.setImageResource(
                    when (viewModel.postMonster.value) {
                        1 -> R.drawable.ic_monster_roompost
                        2 -> R.drawable.ic_monster_yellowblack
                        3 -> R.drawable.ic_monster_unico
                        4 -> R.drawable.ic_monster_firedragon
                        5 -> R.drawable.ic_monster_iceteeth
                        6 -> R.drawable.ic_monster_icehit
                        else -> R.drawable.ic_monster_roompost
                    }
                )
                viewModel.event.value?.userId = UserManager.userData.id.toString()
            }
        })

        val arms = arrayListOf(
            "皆可",
            "太刀", "大劍",
            "弓箭", "充能斧",
            "輕弩", "雙劍",
            "操蟲棍", "重弩",
            "大錘", "銃槍", "單手劍",
            "長槍", "斬擊斧", "狩獵笛"
        )
        val missionType = arrayListOf("選擇任務類型", "任務", "自由", "調查", "活動", "限時活動", "採集")
        val monsterName = arrayListOf("選擇魔物類型", "滅盡龍", "煌黑龍", "麒麟", "火龍", "冰牙龍", "冰呪龍")
        val adapterArmsType = ArrayAdapter(
            MonsterApplication.instance,
            android.R.layout.simple_spinner_dropdown_item,
            arms
        )

        val adapterActivityType = ArrayAdapter(
            MonsterApplication.instance,
            android.R.layout.simple_spinner_dropdown_item,
            missionType
        )
        val adapterMonsterName = ArrayAdapter(
            MonsterApplication.instance,
            android.R.layout.simple_spinner_dropdown_item,
            monsterName
        )
        // UI Spinner change
        binding.chatRoomPostArmsA.adapter = adapterArmsType
        binding.chatRoomPostArmsB.adapter = adapterArmsType
        binding.chatRoomPostArmsC.adapter = adapterArmsType
        binding.chatRoomPostArmsD.adapter = adapterArmsType
        binding.chatRoomPostTypeNameTextSpin.adapter = adapterActivityType
        binding.chatRoomPostMonsterNameTextSpin.adapter = adapterMonsterName
        // Info Spinner return
        binding.chatRoomPostArmsA.onItemSelectedListener = getSpinnerItemListner(1)
        binding.chatRoomPostArmsB.onItemSelectedListener = getSpinnerItemListner(2)
        binding.chatRoomPostArmsC.onItemSelectedListener = getSpinnerItemListner(3)
        binding.chatRoomPostArmsD.onItemSelectedListener = getSpinnerItemListner(4)
        binding.chatRoomPostTypeNameTextSpin.onItemSelectedListener = getSpinnerItemListner(5)
        binding.chatRoomPostMonsterNameTextSpin.onItemSelectedListener = getSpinnerItemListner(6)
        // get now time
        viewModel.getTime.observe(viewLifecycleOwner, Observer {
            val localDate = LocalDate.now()
            val localTime = LocalTime.now()
            val addTime = "$localDate $localTime"
            viewModel.getWorkerMangerTime(addTime)
        })

        viewModel.leave.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigateUp()
                viewModel.onLeft()
            }
        })
        return binding.root
    }

    private fun getSpinnerItemListner(number: Long): AdapterView.OnItemSelectedListener? {
        return  object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (number) {
                    1L -> viewModel.setArmsNameByMember(1, getArmsNameByNum(id))
                    2L -> viewModel.setArmsNameByMember(2, getArmsNameByNum(id))
                    3L -> viewModel.setArmsNameByMember(3, getArmsNameByNum(id))
                    4L -> viewModel.setArmsNameByMember(4, getArmsNameByNum(id))
                    5L -> viewModel.setPostTypeName(id)
                    else -> viewModel.setMonsterType(id)
                }
            }
        }
    }

    private fun getArmsNameByNum(id: Long): String {
        return when (id) {
            0L -> "皆可"
            1L -> "太刀"
            2L -> "大劍"
            3L -> "弓箭"
            4L -> "充能斧"
            5L -> "輕弩"
            6L -> "雙劍"
            7L -> "操蟲棍"
            8L -> "重弩"
            9L -> "大錘"
            10L -> "銃槍"
            11L -> "單手劍"
            12L -> "長槍"
            13L -> "斬擊斧"
            14L -> "狩獵笛"
            else -> ""
        }
    }

    @SuppressLint("SetTextI18n")
    fun datePicker() {
        val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            calender.set(year, month, day)
            format("yyyy-MM-dd")
            viewModel.getDate(year, month, day)
            binding.chatRoomPostDateText.text = "$year-${month.plus(1)}-$day"
        }
        context?.let {
            DatePickerDialog(
                it,
                dateListener,
                calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    @SuppressLint("SetTextI18n")
    fun timePicker() {
        val timeListener = TimePickerDialog.OnTimeSetListener { _, hour, min ->
            calender.set(Calendar.HOUR_OF_DAY, hour)
            calender.set(Calendar.MINUTE, min)
            format("HH:mm")
            viewModel.getTime(hour, min)
            binding.chatRoomPostTimeText.text = "$hour : $min "
        }
        TimePickerDialog(
            context,
            timeListener,
            calender.get(Calendar.HOUR_OF_DAY),
            calender.get(Calendar.MINUTE),
            true
        ).show()
    }
}
