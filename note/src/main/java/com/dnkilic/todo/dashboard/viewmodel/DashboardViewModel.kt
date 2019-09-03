package com.dnkilic.todo.dashboard.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnkilic.todo.core.extension.failed
import com.dnkilic.todo.core.extension.loading
import com.dnkilic.todo.core.extension.success
import com.dnkilic.todo.core.model.Resource
import com.dnkilic.todo.dashboard.model.DashboardContract
import com.dnkilic.todo.data.NotesDependencyHolder
import com.dnkilic.todo.data.json.Note
import kotlinx.coroutines.launch
import timber.log.Timber

class DashboardViewModel(private val repo: DashboardContract.Repository) : ViewModel() {

    private val notesMutableLiveData = MutableLiveData<Resource<List<Note>>>()
    val notesResource = notesMutableLiveData

    fun getNotes() {
        if (notesResource.value == null) {
            notesMutableLiveData.loading(true)
            viewModelScope.launch {
                try {
                    val notes = repo.getNotes()
                    notesMutableLiveData.success(notes)
                } catch (exception: Exception) {
                    Timber.e(exception)
                    handleError(notesMutableLiveData, exception)
                }
            }
        }
    }

    fun complete(noteIds: List<Long>) {
        viewModelScope.launch {
            try {
                repo.completeNotes(noteIds)
                val notes = repo.getNotes()
                notesMutableLiveData.success(notes)
            } catch (exception: Exception) {
                Timber.e(exception)
                handleError(notesMutableLiveData, exception)
            }
        }
    }

    fun delete(noteIds: List<Long>) {
        viewModelScope.launch {
            try {
                repo.deleteNotes(noteIds)
                val notes = repo.getNotes()
                notesMutableLiveData.success(notes)
            } catch (exception: Exception) {
                Timber.e(exception)
                handleError(notesMutableLiveData, exception)
            }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            try {
                val notes = repo.searchNotes(query)
                notesMutableLiveData.success(notes)
            } catch (exception: Exception) {
                Timber.e(exception)
            }
        }
    }

    private fun handleError(itemMutableLiveData: MutableLiveData<Resource<List<Note>>>, throwable: Throwable) {
        itemMutableLiveData.failed(throwable)
    }

    override fun onCleared() {
        super.onCleared()
        NotesDependencyHolder.destroyDashboardComponent()
    }
}