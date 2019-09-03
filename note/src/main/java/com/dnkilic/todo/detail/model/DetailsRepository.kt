package com.dnkilic.todo.detail.model

import com.dnkilic.todo.data.json.Note

class DetailsRepository(private val local: DetailsContract.Local): DetailsContract.Repository {

    override suspend fun getNote(id: Long): Note {
        return local.getNoteBy(id)
    }

    override suspend fun editNote(note: Note) {
        local.updateNote(note)
    }

    override suspend fun createNote(note: Note) {
        local.insertNote(note)
    }
}