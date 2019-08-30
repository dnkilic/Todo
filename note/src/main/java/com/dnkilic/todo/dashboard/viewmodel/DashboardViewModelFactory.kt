package com.dnkilic.todo.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dnkilic.todo.dashboard.model.DashboardContract

@Suppress("UNCHECKED_CAST")
class DashboardViewModelFactory(private val repository: DashboardContract.Repository) :
        ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DashboardViewModel(repository) as T
    }
}