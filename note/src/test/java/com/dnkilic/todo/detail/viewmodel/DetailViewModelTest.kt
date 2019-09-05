package com.dnkilic.todo.detail.viewmodel

import android.os.Build
import androidx.lifecycle.Observer
import com.dnkilic.todo.FakeNoteFactory
import com.dnkilic.todo.core.extension.loading
import com.dnkilic.todo.core.extension.success
import com.dnkilic.todo.core.model.Resource
import com.dnkilic.todo.data.json.Note
import com.dnkilic.todo.detail.model.DetailsContract
import io.mockk.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Tests for [DetailViewModelTest]
 * */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel

    private val repo: DetailsContract.Repository = mockk()

    private val noteResource: Observer<Resource<Note>> = spyk()

    private val operationResource: Observer<Resource<Long>> = spyk()

    @Before
    fun init() {
        viewModel = DetailViewModel(repo)
        viewModel.noteResource.observeForever(noteResource)
        viewModel.operationResource.observeForever(operationResource)
    }

    @Test
    fun testCreateNoteSuccess() {
        val dummyNote = FakeNoteFactory.createNote(101)

        viewModel.createNote(dummyNote)
        coVerify { repo.createNote(dummyNote) }

        viewModel.noteResource.success(dummyNote)
        coVerify { noteResource.onChanged(Resource.success(dummyNote)) }
    }

    @Test
    fun testUpdateNoteSuccess() {
        val dummyNote = FakeNoteFactory.createNote(101)

        viewModel.updateNote(dummyNote)
        coVerify { repo.editNote(dummyNote) }

        viewModel.noteResource.success(dummyNote)
        coVerify { noteResource.onChanged(Resource.success(dummyNote)) }
    }

    @Test
    fun testGetNoteSuccess() {
        val dummyNote = FakeNoteFactory.createNote(1)

        coEvery{ repo.getNote(1)} returns dummyNote

        viewModel.getNote(1)
        coVerify { repo.getNote(1) }

        viewModel.noteResource.loading(true)
        coVerify { noteResource.onChanged(Resource.loading(true)) }

        viewModel.noteResource.loading(false)
        coVerify { noteResource.onChanged(Resource.loading(false)) }

        viewModel.noteResource.success(dummyNote)
        coVerify { noteResource.onChanged(Resource.success(dummyNote)) }
    }
}