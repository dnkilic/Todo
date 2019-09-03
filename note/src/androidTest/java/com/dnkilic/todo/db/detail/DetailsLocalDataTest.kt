package com.dnkilic.todo.db.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.dnkilic.todo.FakeNoteFactory
import com.dnkilic.todo.data.local.NoteDatabase
import com.dnkilic.todo.detail.model.DetailsLocalData
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsLocalDataTest {

    private lateinit var noteDatabase: NoteDatabase
    private val detailsLocalData: DetailsLocalData by lazy { DetailsLocalData(noteDatabase) }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dummyItem = FakeNoteFactory.createNote(101)

    @Before
    fun init() {
        noteDatabase = Room
            .inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context,
                NoteDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @Test
    fun getNoteById() {
        runBlocking { noteDatabase.noteDao().insert(dummyItem) }
        val expected = runBlocking { detailsLocalData.getNoteBy(101) }
        Assert.assertEquals(dummyItem.toString(), expected.toString())
    }

    @Test
    fun updateNote() {
        runBlocking { noteDatabase.noteDao().insert(dummyItem) }
        val newItem = dummyItem.copy(title = "newFoo")
        runBlocking { detailsLocalData.updateNote(newItem) }
        val expected = runBlocking { noteDatabase.noteDao().getNoteBy(101) }
        Assert.assertEquals(expected.toString(), newItem.toString())
    }

    @Test
    fun createNote() {
        runBlocking { detailsLocalData.insertNote(dummyItem) }
        val expected = runBlocking { noteDatabase.noteDao().getNoteBy(101) }
        Assert.assertEquals(expected.toString(), dummyItem.toString())
    }

    @After
    fun clean() {
        noteDatabase.close()
    }

}