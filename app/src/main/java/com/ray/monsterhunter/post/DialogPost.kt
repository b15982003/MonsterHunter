package com.ray.monsterhunter.post

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ray.monsterhunter.MainViewModel
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.R
import com.ray.monsterhunter.databinding.DialogPostFragmentBinding
import com.ray.monsterhunter.ext.getVmFactory
import com.ray.monsterhunter.util.ImageManger
import com.ray.monsterhunter.util.TimeUtil
import okhttp3.internal.format
import java.text.SimpleDateFormat
import java.util.*


class DialogPost : AppCompatDialogFragment() {


    private val viewModel by viewModels<DialogPostViewModel> {
        getVmFactory()
    }
    private lateinit var binding: DialogPostFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogPost)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogPostFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel




        val missionType = arrayListOf("選擇文章類型", "最新活動", "心情分享", "魔物分析", "攻略分析", "神人搜尋", "趣事分享")
        val monsterName = arrayListOf("選擇魔物類型", "滅盡龍", "煌黑龍", "麒麟", "火龍", "冰牙龍", "冰呪龍")


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
                        0L -> viewModel.crawling.value?.type = "選擇文章類型"
                        1L -> viewModel.crawling.value?.type = "最新活動"
                        2L -> viewModel.crawling.value?.type = "心情分享"
                        3L -> viewModel.crawling.value?.type = "魔物分析"
                        4L -> viewModel.crawling.value?.type = "攻略分析"
                        5L -> viewModel.crawling.value?.type = "神人搜尋"
                        5L -> viewModel.crawling.value?.type = "趣事分享"
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
                        0L -> {
                            viewModel.crawling.value?.monsterType = "隨機攻打生物"
                            viewModel.crawling.value?.image = ImageManger.imageData.monsterRoomPost
                        }
                        1L -> {
                            viewModel.crawling.value?.monsterType = "滅盡龍"
                            viewModel.crawling.value?.image = ImageManger.imageData.monsterRoomPost
                        }
                        2L -> {
                            viewModel.crawling.value?.monsterType = "煌黑龍"
                            viewModel.crawling.value?.image = ImageManger.imageData.monsterYellowBlack
                        }
                        3L -> {
                            viewModel.crawling.value?.monsterType = "麒麟"
                            viewModel.crawling.value?.image = ImageManger.imageData.monsterUnico
                        }
                        4L -> {
                            viewModel.crawling.value?.monsterType = "火龍"
                            viewModel.crawling.value?.image = ImageManger.imageData.monsterFireDragon
                        }
                        5L -> {
                            viewModel.crawling.value?.monsterType = "冰牙龍"
                            viewModel.crawling.value?.image = ImageManger.imageData.monsterIceteeth
                        }
                        5L -> {
                            viewModel.crawling.value?.monsterType = "冰呪龍"
                            viewModel.crawling.value?.image = ImageManger.imageData.monsterIcehit
                        }
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

        viewModel.user.observe(viewLifecycleOwner, Observer {
            it?.let {

                viewModel.crawling.value?.user = it
            }
        })


        return binding.root
    }


}
