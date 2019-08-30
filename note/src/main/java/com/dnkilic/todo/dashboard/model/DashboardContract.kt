package com.dnkilic.todo.dashboard.model

import com.dnkilic.todo.data.json.Note

interface DashboardContract {
    interface Repository {
        suspend fun getNotes(): List<Note>
        suspend fun searchNotes(query: String): List<Note>
        suspend fun getNote(id: Long): Note
        suspend fun deleteNote(id: Long)
        suspend fun deleteNotes(notes: List<Note>)
    }

    interface Local {
        suspend fun getNotes(): List<Note>
        suspend fun searchNotes(query: String): List<Note>
        suspend fun getNote(id: Long): Note
        suspend fun deleteNote(id: Long)
        suspend fun deleteNotes(notes: List<Note>)
    }
}