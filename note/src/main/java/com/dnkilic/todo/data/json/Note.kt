package com.dnkilic.todo.data.json

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo val title: String,
    @ColumnInfo val description: String,
    @ColumnInfo val dueDate: Long,
    @ColumnInfo val tags: List<String>,
    @ColumnInfo val isCompleted: Boolean
): Comparable<Note> {
    override fun compareTo(other: Note) = when {
        !other.isCompleted && isCompleted -> 1
        other.isCompleted && !isCompleted -> -1
        else -> when {
            dueDate == -1L -> 1
            other.dueDate == -1L -> -1
            other.dueDate > dueDate -> -1
            other.dueDate < dueDate -> 1
            else -> 0
        }
    }
}