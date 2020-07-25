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
                        0L -> viewModel.crawling.value?.type = "出擊"
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
                        0L -> viewModel.crawling.value?.monsterType = "隨機攻打生物"
                        1L -> viewModel.crawling.value?.monsterType = "滅盡龍"
                        2L -> viewModel.crawling.value?.monsterType = "煌黑龍"
                        3L -> viewModel.crawling.value?.monsterType = "麒麟"
                        4L -> viewModel.crawling.value?.monsterType = "火龍"
                        5L -> viewModel.crawling.value?.monsterType = "冰牙龍"
                        5L -> viewModel.crawling.value?.monsterType = "冰呪龍"
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
