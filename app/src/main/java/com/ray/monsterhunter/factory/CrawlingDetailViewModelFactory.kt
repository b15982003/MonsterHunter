package com.ray.monsterhunter.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ray.monsterhunter.crawlingdetail.CrawlingDetailViewModel
import com.ray.monsterhunter.data.Crawling
import com.ray.monsterhunter.data.source.MonsterRepository

class CrawlingDetailViewModelFactory constructor(
    private val repository: MonsterRepository,
    private val crawling : Crawling
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) = with(modelClass) {
        when {
            isAssignableFrom(CrawlingDetailViewModel::class.java) ->
                CrawlingDetailViewModel(repository,crawling)


            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }as T
}