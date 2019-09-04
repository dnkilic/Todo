package com.dnkilic.todo.dashboard.model

import com.dnkilic.todo.data.json.Note
import com.dnkilic.todo.data.local.NoteDatabase

class DashboardLocalData(private val noteDatabase: NoteDatabase) : DashboardContract.Local {

    override suspend fun completeNotes(noteIds: List<Long>) {
        noteIds.forEach { noteDatabase.noteDao().complete(it) }
    }

    override suspend fun getNotes() = noteDatabase.noteDao().getAll()


    override suspend fun searchNotes(query: String): List<Note> {
        return noteDatabase.noteDao().search("%$query%")
    }

    override suspend fun getNote(id: Long) = noteDatabase.noteDao().getNoteBy(id)

    override suspend fun deleteNote(id: Long) {
        noteDatabase.noteDao().deleteNoteBy(id)
    }

    override suspend fun deleteNotes(noteIds: List<Long>) {
        noteIds.forEach { noteDatabase.noteDao().deleteNoteBy(it) }
    }

    override suspend fun insert(notes: List<Note>) {
        noteDatabase.noteDao().insertAll(notes)
    }
}