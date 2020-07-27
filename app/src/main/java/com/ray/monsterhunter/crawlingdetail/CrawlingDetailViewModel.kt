package com.ray.monsterhunter.crawlingdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ray.monsterhunter.data.Crawling
import com.ray.monsterhunter.data.source.MonsterRepository

class CrawlingDetailViewModel(
    val repository: MonsterRepository,
    val argument : Crawling
)
    : ViewModel() {
    private val _crawling = MutableLiveData<Crawling>().apply {
        value = argument
    }
    val crawling : LiveData<Crawling>
        get() = _crawling

}
