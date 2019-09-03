package com.dnkilic.todo.data.local

import androidx.room.*
import com.dnkilic.todo.data.json.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes WHERE id == :id")
    suspend fun getNoteBy(id: Long): Note

    @Query("SELECT * FROM notes")
    suspend fun getAll(): List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notes: List<Note>)

    @Query("DELETE FROM notes WHERE id == :id")
    suspend fun deleteNoteBy(id: Long)

    @Delete
    suspend fun delete(notes: List<Note>)

    @Query("UPDATE notes SET isCompleted = 1 WHERE id == :id")
    suspend fun complete(id: Long)
}