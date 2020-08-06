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
import androidx.navigation.fragment.findNavController
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.R
import com.ray.monsterhunter.databinding.DialogPostFragmentBinding
import com.ray.monsterhunter.ext.getVmFactory


class DialogPost : AppCompatDialogFragment() {

    private val viewModel by viewModels<DialogPostViewModel> { getVmFactory() }
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

        // choose Monster type
        binding.postDialogMonstername.onItemSelectedListener = getSpinnerListner(1)
        // choose active type
        binding.postDialogActivityType.onItemSelectedListener = getSpinnerListner(2)

        viewModel.leave.observe(viewLifecycleOwner, Observer {
            it?.let { needRefresh ->
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

    fun getSpinnerListner(type: Long): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?,
                position: Int,
                id: Long
            ) {
                when (type) {
                    1L -> viewModel.chooseMonsterType(id)
                    else -> viewModel.chooseCrawlingType(id)
                }
            }
        }
    }
}
