package com.ray.monsterhunter.post


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.R
import com.ray.monsterhunter.databinding.DialogChatRoomFragmentBinding
import com.ray.monsterhunter.ext.getVmFactory
import com.ray.monsterhunter.util.ImageManger
import com.ray.monsterhunter.util.Logger
import com.ray.monsterhunter.util.TimeUtil
import com.ray.monsterhunter.util.UserManager
import okhttp3.internal.format
import java.text.SimpleDateFormat
import java.util.*

class DialogChatRoom : AppCompatDialogFragment() {


    private val viewModel by viewModels<DialogChatRoomViewModel> { getVmFactory() }
    private lateinit var binding: DialogChatRoomFragmentBinding
    val calender = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogPost)
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DialogChatRoomFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.chatRoomPostDateText.setOnClickListener {
            datePicker()
        }
        binding.chatRoomPostTimeText.setOnClickListener {
            timePicker()
        }



        viewModel.event.observe(viewLifecycleOwner, Observer {
            it.roomName?.let {
                Log.d("eeeeeeee", "${it}")
            }
        })



        viewModel.postMonster.observe(viewLifecycleOwner, Observer {
            it?.let {
                Logger.d(viewModel.postMonster.value.toString())

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
//下拉式選單，不在main activity裡面所以使用yourapp
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
        val adapter = ArrayAdapter(
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

        binding.chatRoomPostArmsA.adapter = adapter
        binding.chatRoomPostArmsB.adapter = adapter
        binding.chatRoomPostArmsC.adapter = adapter
        binding.chatRoomPostArmsD.adapter = adapter
        binding.chatRoomPostTypeNameTextSpin.adapter = adapterActivityType
        binding.chatRoomPostMonsterNameTextSpin.adapter = adapterMonsterName

        binding.chatRoomPostTypeNameTextSpin.onItemSelectedListener =
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
                        0L -> viewModel.event.value?.typeName = "出擊"
                        1L -> viewModel.event.value?.typeName = "任務"
                        2L -> viewModel.event.value?.typeName = "自由"
                        3L -> viewModel.event.value?.typeName = "調查"
                        4L -> viewModel.event.value?.typeName = "活動"
                        5L -> viewModel.event.value?.typeName = "限時活動"
                        5L -> viewModel.event.value?.typeName = "採集"
                    }
                }
            }


        binding.chatRoomPostArmsA.onItemSelectedListener =
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
                        0L -> viewModel.event.value?.armsType1 = "皆可"
                        1L -> viewModel.event.value?.armsType1 = "太刀"
                        2L -> viewModel.event.value?.armsType1 = "大劍"
                        3L -> viewModel.event.value?.armsType1 = "弓箭"
                        4L -> viewModel.event.value?.armsType1 = "充能斧"
                        5L -> viewModel.event.value?.armsType1 = "輕弩"
                        6L -> viewModel.event.value?.armsType1 = "雙劍"
                        7L -> viewModel.event.value?.armsType1 = "操蟲棍"
                        8L -> viewModel.event.value?.armsType1 = "重弩"
                        9L -> viewModel.event.value?.armsType1 = "大錘"
                        10L -> viewModel.event.value?.armsType1 = "銃槍"
                        11L -> viewModel.event.value?.armsType1 = "單手劍"
                        12L -> viewModel.event.value?.armsType1 = "長槍"
                        13L -> viewModel.event.value?.armsType1 = "斬擊斧"
                        14L -> viewModel.event.value?.armsType1 = "狩獵笛"

                    }
                }
            }

        binding.chatRoomPostArmsB.onItemSelectedListener =
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
                        0L -> viewModel.event.value?.armsType2 = "皆可"
                        1L -> viewModel.event.value?.armsType2 = "太刀"
                        2L -> viewModel.event.value?.armsType2 = "大劍"
                        3L -> viewModel.event.value?.armsType2 = "弓箭"
                        4L -> viewModel.event.value?.armsType2 = "充能斧"
                        5L -> viewModel.event.value?.armsType2 = "輕弩"
                        6L -> viewModel.event.value?.armsType2 = "雙劍"
                        7L -> viewModel.event.value?.armsType2 = "操蟲棍"
                        8L -> viewModel.event.value?.armsType2 = "重弩"
                        9L -> viewModel.event.value?.armsType2 = "大錘"
                        10L -> viewModel.event.value?.armsType2 = "銃槍"
                        11L -> viewModel.event.value?.armsType2 = "單手劍"
                        12L -> viewModel.event.value?.armsType2 = "長槍"
                        13L -> viewModel.event.value?.armsType2 = "斬擊斧"
                        14L -> viewModel.event.value?.armsType2 = "狩獵笛"
                    }
                }
            }

        binding.chatRoomPostArmsC.onItemSelectedListener =
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
                        0L -> viewModel.event.value?.armsType3 = "皆可"
                        1L -> viewModel.event.value?.armsType3 = "太刀"
                        2L -> viewModel.event.value?.armsType3 = "大劍"
                        3L -> viewModel.event.value?.armsType3 = "弓箭"
                        4L -> viewModel.event.value?.armsType3 = "充能斧"
                        5L -> viewModel.event.value?.armsType3 = "輕弩"
                        6L -> viewModel.event.value?.armsType3 = "雙劍"
                        7L -> viewModel.event.value?.armsType3 = "操蟲棍"
                        8L -> viewModel.event.value?.armsType3 = "重弩"
                        9L -> viewModel.event.value?.armsType3 = "大錘"
                        10L -> viewModel.event.value?.armsType3 = "銃槍"
                        11L -> viewModel.event.value?.armsType3 = "單手劍"
                        12L -> viewModel.event.value?.armsType3 = "長槍"
                        13L -> viewModel.event.value?.armsType3 = "斬擊斧"
                        14L -> viewModel.event.value?.armsType3 = "狩獵笛"
                    }
                }
            }

        binding.chatRoomPostArmsD.onItemSelectedListener =
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
                        0L -> viewModel.event.value?.armsType4 = "皆可"
                        1L -> viewModel.event.value?.armsType4 = "太刀"
                        2L -> viewModel.event.value?.armsType4 = "大劍"
                        3L -> viewModel.event.value?.armsType4 = "弓箭"
                        4L -> viewModel.event.value?.armsType4 = "充能斧"
                        5L -> viewModel.event.value?.armsType4 = "輕弩"
                        6L -> viewModel.event.value?.armsType4 = "雙刀"
                        7L -> viewModel.event.value?.armsType4 = "操蟲棍"
                        8L -> viewModel.event.value?.armsType4 = "重弩"
                        9L -> viewModel.event.value?.armsType4 = "大錘"
                        10L -> viewModel.event.value?.armsType4 = "銃槍"
                        11L -> viewModel.event.value?.armsType4 = "單手劍"
                        12L -> viewModel.event.value?.armsType4 = "長槍"
                        13L -> viewModel.event.value?.armsType4 = "斬擊斧"
                        14L -> viewModel.event.value?.armsType4 = "狩獵笛"
                    }
                }
            }



        binding.chatRoomPostMonsterNameTextSpin.onItemSelectedListener =
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
                            viewModel.event.value?.monsterName = "隨機打"
                            viewModel.postMonster.value = 0
                            viewModel.event.value?.image = ImageManger.imageData.monsterRoomPost
                        }
                        1L -> {
                            viewModel.event.value?.monsterName = "滅盡龍"
                            viewModel.postMonster.value = 1
                            viewModel.event.value?.image = ImageManger.imageData.monsterRoomPost
                        }
                        2L -> {
                            viewModel.event.value?.monsterName = "煌黑龍"
                            viewModel.postMonster.value = 2
                            viewModel.event.value?.image = ImageManger.imageData.monsterYellowBlack
                        }
                        3L -> {
                            viewModel.event.value?.monsterName = "麒麟"
                            viewModel.postMonster.value = 3
                            viewModel.event.value?.image = ImageManger.imageData.monsterUnico
                        }
                        4L -> {
                            viewModel.event.value?.monsterName = "火龍"
                            viewModel.postMonster.value = 4
                            viewModel.event.value?.image = ImageManger.imageData.monsterFireDragon
                        }
                        5L -> {
                            viewModel.event.value?.monsterName = "冰牙龍"
                            viewModel.postMonster.value = 5
                            viewModel.event.value?.image = ImageManger.imageData.monsterIceteeth
                        }
                        6L -> {
                            viewModel.event.value?.monsterName = "冰呪龍"
                            viewModel.postMonster.value = 6
                            viewModel.event.value?.image = ImageManger.imageData.monsterIcehit
                        }
                    }
                }
            }

        viewModel.leave.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigateUp()
                viewModel.onLeft()
            }
        })

        return binding.root
    }

    fun datePicker() {
        val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            calender.set(year, month, day)
            format("yyyy-MM-dd")
            val dateToStamp = TimeUtil.DateToStamp("$year-$month-$day", Locale.TAIWAN)
            viewModel.event.value?.dateTime?.date = dateToStamp

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

    fun timePicker() {
        val timeListener = TimePickerDialog.OnTimeSetListener { _, hour, min ->
            calender.set(Calendar.HOUR_OF_DAY, hour)
            calender.set(Calendar.MINUTE, min)
            format("HH:mm")
            val timeToStamp = TimeUtil.TimeToStamp("$hour:$min", Locale.TAIWAN)
            viewModel.event.value?.dateTime?.time = timeToStamp

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

    fun format(format: String, view: View) {
        val time = SimpleDateFormat(format, Locale.TAIWAN)
        (view as EditText).setText(time.format(calender.time))
    }


}
