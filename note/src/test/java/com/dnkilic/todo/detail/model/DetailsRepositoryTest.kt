package com.dnkilic.todo.detail.model

import android.os.Build
import com.dnkilic.todo.FakeNoteFactory
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class DetailsRepositoryTest {

    private val local: DetailsContract.Local = spyk()

    private lateinit var repository: DetailsRepository

    private val dummyItem = FakeNoteFactory.createNote(101)

    @Before
    fun init() {
        repository = DetailsRepository(local)
    }

    @Test
    fun testGetNote() {
        val expected = dummyItem
        coEvery { local.getNoteBy(any()) } returns expected

        val actual = runBlocking { repository.getNote(dummyItem.id) }

        Assert.assertEquals(expected, actual)
    }

    @Test(expected = IOException::class)
    fun testErrorWhenGetNote() {
        val exception = IOException()
        coEvery { local.getNoteBy(any()) } throws exception

        runBlocking { repository.getNote(101) }

        coVerify { local.getNoteBy(any()) wasNot(Called) }
    }

    @Test
    fun testEditNote() {
        val item = dummyItem
        val edited = item.copy(title = "newFoo")
        coEvery { local.getNoteBy(any()) } returns edited

        runBlocking { repository.editNote(edited) }
        val new = runBlocking { local.getNoteBy(101) }

        Assert.assertEquals(edited, new)
    }

    @Test(expected = IOException::class)
    fun testErrorWhenEditNote() {
        val exception = IOException()
        coEvery { local.updateNote(any()) } throws exception

        runBlocking { repository.editNote(dummyItem) }

        coVerify { local.getNoteBy(any()) wasNot(Called) }
    }

    @Test
    fun testInsertNote() {
        val expected = dummyItem
        coEvery { local.getNoteBy(any()) } returns expected

        runBlocking { repository.createNote(expected) }
        val new = runBlocking { local.getNoteBy(101) }

        Assert.assertEquals(expected, new)
    }

    @Test(expected = IOException::class)
    fun testErrorWhenInsertNote() {
        val exception = IOException()
        coEvery { local.insertNote(any()) } throws exception

        runBlocking { repository.createNote(dummyItem) }

        coVerify { local.getNoteBy(any()) wasNot(Called) }
    }

}