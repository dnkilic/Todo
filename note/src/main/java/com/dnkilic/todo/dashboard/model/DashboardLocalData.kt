package com.dnkilic.todo.dashboard.model

import com.dnkilic.todo.data.json.Note
import com.dnkilic.todo.data.local.NoteDatabase

class DashboardLocalData(private val noteDatabase: NoteDatabase) : DashboardContract.Local {

    override suspend fun getNotes() = noteDatabase.noteDao().getAll()


    override suspend fun searchNotes(query: String): List<Note> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getNote(id: Long) = noteDatabase.noteDao().getNoteBy(id)

    override suspend fun deleteNote(id: Long) {
        noteDatabase.noteDao().deleteNoteBy(id)
    }

    override suspend fun deleteNotes(notes: List<Note>) {
        noteDatabase.noteDao().delete(notes)
    }
}