package com.dnkilic.todo.detail.viewmodel

import androidx.lifecycle.ViewModel
import com.dnkilic.todo.data.NotesDependencyHolder
import com.dnkilic.todo.detail.model.DetailsContract

class DetailViewModel(private val repo: DetailsContract.Repository) : ViewModel() {
    // TODO: Implement the ViewModel

    override fun onCleared() {
        super.onCleared()
        NotesDependencyHolder.destroyDetailsComponent()
    }
}
