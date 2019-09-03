package com.dnkilic.todo.detail.model

import com.dnkilic.todo.data.json.Note
import com.dnkilic.todo.data.local.NoteDatabase

class DetailsLocalData(private val noteDatabase: NoteDatabase): DetailsContract.Local {

    override suspend fun getNoteBy(id: Long): Note {
       return noteDatabase.noteDao().getNoteBy(id)
    }

    override suspend fun updateNote(note: Note) {
        return noteDatabase.noteDao().insert(note)
    }

    override suspend fun insertNote(note: Note) {
        return noteDatabase.noteDao().insert(note)
    }
}