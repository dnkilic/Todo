package com.dnkilic.todo.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dnkilic.todo.FakeNoteFactory
import com.dnkilic.todo.data.local.NoteDatabase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoteDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: NoteDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @Test
    fun checkDbNotNull() {
        MatcherAssert.assertThat(db, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(db.noteDao(), CoreMatchers.notNullValue())
    }

    @Test
    fun insertAndRead() = runBlocking {
        db.noteDao().insert(FakeNoteFactory.createNote(101))
        val loaded = db.noteDao().getNoteBy(id = 101)

        // verify
        MatcherAssert.assertThat(loaded, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(loaded.title, CoreMatchers.`is`("foo"))
        MatcherAssert.assertThat(loaded.description, CoreMatchers.`is`("bar"))
        MatcherAssert.assertThat(loaded.id, CoreMatchers.equalTo(101L))
    }


    @Test
    fun insertAndDelete() = runBlocking {
        // act
        db.noteDao().insert(FakeNoteFactory.createNote(101))
        db.noteDao().deleteNoteBy(101)
        val loaded = db.noteDao().getNoteBy(101)

        // verify
        MatcherAssert.assertThat(loaded, CoreMatchers.nullValue())
    }

    @Test
    fun insertMultipleAndGetMultiple() = runBlocking {
        // arrange
        val articles = FakeNoteFactory.createNotes(3)

        // act
        db.noteDao().insertAll(articles)
        val loaded = db.noteDao().getAll()

        // verify
        MatcherAssert.assertThat(loaded.size, CoreMatchers.equalTo(articles.size))
    }

    @Test
    fun insertMultipleAndDeleteMultiple() = runBlocking {
        // arrange
        val articles = FakeNoteFactory.createNotes(3)

        // act
        db.noteDao().insertAll(articles)
        db.noteDao().delete(articles)
        val loaded = db.noteDao().getAll()

        // verify
        MatcherAssert.assertThat(loaded.size, CoreMatchers.equalTo(0))
    }

    @After
    fun clean() {
        db.close()
    }
}