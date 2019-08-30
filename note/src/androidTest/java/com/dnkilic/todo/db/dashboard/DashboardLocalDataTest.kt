package com.dnkilic.todo.db.dashboard

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dnkilic.todo.FakeNoteFactory
import com.dnkilic.todo.dashboard.model.DashboardLocalData
import com.dnkilic.todo.data.local.NoteDatabase
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DashboardLocalDataTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: NoteDatabase

    private val dashboardLocalData : DashboardLocalData by lazy { DashboardLocalData(db) }

    private val dummyNotes = FakeNoteFactory.createNotes(5)

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @Test
    fun getNotes() {
        runBlocking { db.noteDao().insertAll(dummyNotes) }

        val actual = dummyNotes
        val expected = runBlocking { dashboardLocalData.getNotes() }
        Assert.assertEquals(actual.joinToString(separator = ""), expected.joinToString(separator = ""))
    }

    @Test
    fun getNote() {
        val actual = FakeNoteFactory.createNote(101)
        runBlocking { db.noteDao().insert(actual) }

        val expected = runBlocking { dashboardLocalData.getNote(101) }
        Assert.assertEquals(actual.id, expected.id)
    }

    @Test
    fun deleteNotes() = runBlocking {
        db.noteDao().insertAll(dummyNotes)
        dashboardLocalData.deleteNotes(dummyNotes)
        val notes = db.noteDao().getAll()
        Assert.assertEquals(notes.size, 0)
    }

    @After
    fun clean() {
        db.close()
    }
}