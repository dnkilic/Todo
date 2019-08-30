package com.dnkilic.todo.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import com.dnkilic.todo.dashboard.model.DashboardContract
import com.dnkilic.todo.data.NotesDependencyHolder

class DashboardViewModel(private val repo: DashboardContract.Repository) : ViewModel() {

    fun getNotes() {

    }

    override fun onCleared() {
        super.onCleared()
        NotesDependencyHolder.destroy()
    }
}