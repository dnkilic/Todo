package com.dnkilic.todo.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dnkilic.todo.detail.model.DetailsContract

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val repository: DetailsContract.Repository) :
        ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(repository) as T
    }
}