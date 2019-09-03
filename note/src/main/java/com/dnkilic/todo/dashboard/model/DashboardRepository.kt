package com.dnkilic.todo.dashboard.model

import com.dnkilic.todo.data.json.Note

class DashboardRepository(private val local: DashboardContract.Local) : DashboardContract.Repository {

    override suspend fun completeNotes(noteIds: List<Long>) {
        local.completeNotes(noteIds)
    }

    override suspend fun getNotes() = local.getNotes()

    override suspend fun searchNotes(query: String): List<Note> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getNote(id: Long) = local.getNote(id)

    override suspend fun deleteNote(id: Long) {
        local.deleteNote(id)
    }

    override suspend fun deleteNotes(noteIds: List<Long>) {
        local.deleteNotes(noteIds)
    }
}