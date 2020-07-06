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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ray.monsterhunter.MainViewModel
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.R
import com.ray.monsterhunter.databinding.DialogPostFragmentBinding
import com.ray.monsterhunter.ext.getVmFactory


class DialogPost : AppCompatDialogFragment() {


    private val viewModel by viewModels<DialogPostViewModel> {
        getVmFactory()
    }
    private lateinit var binding: DialogPostFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogPost)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogPostFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        //下拉式選單，不在main activity裡面所以使用yourapp
        val arms = arrayListOf("皆可", "太刀", "長槍", "斬擊斧", "雙刀", "操蟲棍")
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
        binding.postDialogArmsA.adapter = adapter
        binding.postDialogArmsB.adapter = adapter
        binding.postDialogArmsC.adapter = adapter
        binding.postDialogArmsD.adapter = adapter
        binding.postDialogActivityType.adapter = adapterActivityType
        binding.postDialogMonstername.adapter = adapterMonsterName


        binding.postDialogActivityType.onItemSelectedListener =
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
                        0L -> viewModel.crawling.value?.type = "null"
                        1L -> viewModel.crawling.value?.type = "任務"
                        2L -> viewModel.crawling.value?.type = "自由"
                        3L -> viewModel.crawling.value?.type = "調查"
                        4L -> viewModel.crawling.value?.type = "活動"
                        5L -> viewModel.crawling.value?.type = "限時活動"
                        5L -> viewModel.crawling.value?.type = "採集"
                    }
                }
            }


        binding.postDialogMonstername.onItemSelectedListener =
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
                        0L -> viewModel.crawling.value?.monsterType = "隨機"
                        1L -> viewModel.crawling.value?.monsterType = "滅盡龍"
                        2L -> viewModel.crawling.value?.monsterType = "煌黑龍"
                        3L -> viewModel.crawling.value?.monsterType = "麒麟"
                        4L -> viewModel.crawling.value?.monsterType = "火龍"
                        5L -> viewModel.crawling.value?.monsterType = "冰牙龍"
                        5L -> viewModel.crawling.value?.monsterType = "冰呪龍"
                    }
                }
            }


        binding.postDialogArmsA.onItemSelectedListener =
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
                        0L -> viewModel.crawling.value?.armsType1 = "皆可"
                        1L -> viewModel.crawling.value?.armsType1 = "太刀"
                        2L -> viewModel.crawling.value?.armsType1 = "長槍"
                        3L -> viewModel.crawling.value?.armsType1 = "斬擊斧"
                        4L -> viewModel.crawling.value?.armsType1 = "雙刀"
                        5L -> viewModel.crawling.value?.armsType1 = "操蟲棍"
                    }
                }
            }

        binding.postDialogArmsB.onItemSelectedListener =
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
                        0L -> viewModel.crawling.value?.armsType2 = "皆可"
                        1L -> viewModel.crawling.value?.armsType2 = "太刀"
                        2L -> viewModel.crawling.value?.armsType2 = "長槍"
                        3L -> viewModel.crawling.value?.armsType2 = "斬擊斧"
                        4L -> viewModel.crawling.value?.armsType2 = "雙刀"
                        5L -> viewModel.crawling.value?.armsType2 = "操蟲棍"
                    }
                }
            }

        binding.postDialogArmsC.onItemSelectedListener =
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
                        0L -> viewModel.crawling.value?.armsType3 = "皆可"
                        1L -> viewModel.crawling.value?.armsType3 = "太刀"
                        2L -> viewModel.crawling.value?.armsType3 = "長槍"
                        3L -> viewModel.crawling.value?.armsType3 = "斬擊斧"
                        4L -> viewModel.crawling.value?.armsType3 = "雙刀"
                        5L -> viewModel.crawling.value?.armsType3 = "操蟲棍"
                    }
                }
            }

        binding.postDialogArmsD.onItemSelectedListener =
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
                        0L -> viewModel.crawling.value?.armsType4 = "皆可"
                        1L -> viewModel.crawling.value?.armsType4 = "太刀"
                        2L -> viewModel.crawling.value?.armsType4 = "長槍"
                        3L -> viewModel.crawling.value?.armsType4 = "斬擊斧"
                        4L -> viewModel.crawling.value?.armsType4 = "雙刀"
                        5L -> viewModel.crawling.value?.armsType4 = "操蟲棍"
                    }
                }
            }

        viewModel.leave.observe(viewLifecycleOwner, Observer {
            it?.let { needRefresh ->
                if (needRefresh) {
                    ViewModelProvider(requireActivity()).get(MainViewModel::class.java).apply {
                        refresh()
                    }
                }
                findNavController().navigateUp()
                viewModel.onLeft()
            }
        })

        return binding.root
    }


}
