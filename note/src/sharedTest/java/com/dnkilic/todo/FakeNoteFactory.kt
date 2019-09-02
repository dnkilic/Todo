package com.dnkilic.todo

import androidx.annotation.VisibleForTesting
import com.dnkilic.todo.data.json.Note

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
object FakeNoteFactory : FakeNote {

    override fun createNote(id: Long): Note {
        return Note(
            id = id,
            title = "foo",
            description = "bar",
            dueDate = 10,
            tags = emptyList(),
            isCompleted = false
        )
    }

    override fun createNotes(count: Int): List<Note> {
        return (1..count).map {
            createNote(it.toLong())
        }
    }
}