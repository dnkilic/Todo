package com.dnkilic.todo.dashboard.viewmodel

import android.os.Build
import androidx.lifecycle.Observer
import com.dnkilic.todo.FakeNoteFactory
import com.dnkilic.todo.core.extension.failed
import com.dnkilic.todo.core.extension.loading
import com.dnkilic.todo.core.extension.success
import com.dnkilic.todo.core.model.Resource
import com.dnkilic.todo.dashboard.model.DashboardContract
import com.dnkilic.todo.data.json.Note
import io.mockk.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException
import java.lang.NullPointerException

/**
 * Tests for [DashboardViewModel]
 * */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class DashboardViewModelTest {

    private lateinit var viewModel: DashboardViewModel

    private val repo: DashboardContract.Repository = mockk()

    private val noteResource: Observer<Resource<List<Note>>> = spyk()

    @Before
    fun init() {
        viewModel = DashboardViewModel(repo)
        viewModel.notesResource.observeForever(noteResource)
    }

    @Test
    fun testGetNotesSuccess() {
        val dummyNotes = FakeNoteFactory.createNotes(2)

        coEvery{ repo.getNotes()} returns dummyNotes

        viewModel.getNotes()
        coVerify { repo.getNotes() }

        viewModel.notesResource.loading(true)
        coVerify { noteResource.onChanged(Resource.loading(true)) }

        viewModel.notesResource.loading(false)
        coVerify { noteResource.onChanged(Resource.loading(false)) }

        viewModel.notesResource.success(dummyNotes)
        coVerify { noteResource.onChanged(Resource.success(dummyNotes)) }
    }

    @Test
    fun testGetNotesError() {
        val exception = IOException()

        coEvery{ repo.getNotes()} throws exception

        viewModel.getNotes()
        coVerify { repo.getNotes() }

        viewModel.notesResource.loading(true)
        coVerify { noteResource.onChanged(Resource.loading(true)) }

        viewModel.notesResource.loading(false)
        coVerify { noteResource.onChanged(Resource.loading(false)) }

        verify { noteResource.onChanged(Resource.failure(exception)) }
    }

    @Test
    fun testGetNotesError_null() {
        val exception = NullPointerException()
        viewModel.notesResource.failed(exception)
        verify { noteResource.onChanged(Resource.failure(exception)) }
    }

    @Test
    fun testCompleteNotesSuccess() {
        val dummyNotes = FakeNoteFactory.createNotes(2)

        viewModel.complete(dummyNotes.map { it.id })
        coVerify { repo.completeNotes(dummyNotes.map { it.id }) }

        viewModel.notesResource.success(dummyNotes)
        coVerify { noteResource.onChanged(Resource.success(dummyNotes)) }
    }

    @Test
    fun testCompleteNotesError() {
        val exception = IOException()
        val dummyNotes = FakeNoteFactory.createNotes(2)

        coEvery{ repo.completeNotes(any())} throws exception

        viewModel.complete(dummyNotes.map { it.id })
        coVerify { repo.completeNotes(dummyNotes.map { it.id }) }

        viewModel.notesResource.failed(exception)
        verify { noteResource.onChanged(Resource.failure(exception)) }
    }

    @Test
    fun testDeleteNotesSuccess() {
        val dummyNotes = FakeNoteFactory.createNotes(2)

        viewModel.delete(dummyNotes.map { it.id })
        coVerify { repo.deleteNotes(dummyNotes.map { it.id }) }

        viewModel.notesResource.success(dummyNotes)
        coVerify { noteResource.onChanged(Resource.success(dummyNotes)) }
    }

    @Test
    fun testDeleteNotesError() {
        val exception = IOException()
        val dummyNotes = FakeNoteFactory.createNotes(2)

        coEvery{ repo.deleteNotes(any())} throws exception

        viewModel.delete(dummyNotes.map { it.id })
        coVerify { repo.deleteNotes(dummyNotes.map { it.id }) }

        viewModel.notesResource.failed(exception)
        verify { noteResource.onChanged(Resource.failure(exception)) }
    }
}