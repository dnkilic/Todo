package com.dnkilic.todo.dashboard.model

import android.os.Build
import com.dnkilic.todo.FakeNoteFactory
import io.mockk.coEvery
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Tests for [DashboardRepository]
 * */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class DashboardRepositoryTest {

    private val local: DashboardContract.Local = spyk()

    private lateinit var repository: DashboardRepository

    private val dummyNotes = FakeNoteFactory.createNotes(2)

    @Before
    fun init() {
        repository = DashboardRepository(local)
    }

    @Test
    fun testGetAllNotes() {
        val expected = dummyNotes
        coEvery { local.getNotes() } returns expected

        val actual = runBlocking { repository.getNotes() }

        Assert.assertEquals(expected, actual)
    }
}