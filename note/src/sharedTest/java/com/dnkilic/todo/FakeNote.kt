package com.dnkilic.todo

import androidx.annotation.VisibleForTesting
import com.dnkilic.todo.data.json.Note

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
interface FakeNote {
    fun createNote(id: Long): Note
    fun createNotes(count: Int): List<Note>
}