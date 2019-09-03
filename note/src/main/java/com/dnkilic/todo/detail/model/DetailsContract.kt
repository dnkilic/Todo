package com.dnkilic.todo.detail.model

import com.dnkilic.todo.data.json.Note

class DetailsContract {
    interface Repository {
        suspend fun getNote(id: Long): Note
        suspend fun editNote(note: Note)
        suspend fun createNote(note: Note)
    }

    interface Local {
        suspend fun getNoteBy(id: Long): Note
        suspend fun updateNote(note: Note)
        suspend fun insertNote(note: Note)
    }
}