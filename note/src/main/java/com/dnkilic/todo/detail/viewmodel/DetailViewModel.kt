package com.dnkilic.todo.detail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnkilic.todo.core.extension.failed
import com.dnkilic.todo.core.extension.loading
import com.dnkilic.todo.core.extension.success
import com.dnkilic.todo.core.model.Resource
import com.dnkilic.todo.data.NotesDependencyHolder
import com.dnkilic.todo.data.json.Note
import com.dnkilic.todo.detail.model.DetailsContract
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailViewModel(private val repo: DetailsContract.Repository) : ViewModel() {

    private val noteMutableLiveData = MutableLiveData<Resource<Note>>()
    val noteResource = noteMutableLiveData

    private val operationMutableLiveData = MutableLiveData<Resource<Long>>()
    val operationResource = operationMutableLiveData

    fun createNote(note: Note) {
        viewModelScope.launch {
            try {
                repo.createNote(note)
                operationMutableLiveData.success(note.id)
            } catch (exception: Exception) {
                Timber.e(exception)
                operationMutableLiveData.failed(exception)
            }
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            try {
                repo.editNote(note)
                operationMutableLiveData.success(note.id)
            } catch (exception: Exception) {
                Timber.e(exception)
                operationMutableLiveData.failed(exception)
            }
        }
    }

    fun getNote(id: Long) {
        if (noteResource.value == null) {
            noteMutableLiveData.loading(true)
            viewModelScope.launch {
                try {
                    val notes = repo.getNote(id)
                    noteMutableLiveData.success(notes)
                } catch (exception: Exception) {
                    Timber.e(exception)
                    handleError(noteMutableLiveData, exception)
                }
            }
        }
    }

    private fun handleError(itemMutableLiveData: MutableLiveData<Resource<Note>>, throwable: Throwable) {
        itemMutableLiveData.failed(throwable)
    }

    override fun onCleared() {
        super.onCleared()
        NotesDependencyHolder.destroyDetailsComponent()
    }
}
