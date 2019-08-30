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
    @ColumnInfo val tags: List<String>
): Comparable<Note> {
    override fun compareTo(other: Note) = when {
        other.dueDate > dueDate -> -1
        other.dueDate < dueDate -> 1
        else -> 0
    }
}