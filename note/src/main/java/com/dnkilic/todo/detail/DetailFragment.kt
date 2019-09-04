package com.dnkilic.todo.detail

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dnkilic.todo.R
import com.dnkilic.todo.core.base.BaseFragment
import com.dnkilic.todo.core.extension.visible
import com.dnkilic.todo.core.model.Resource
import com.dnkilic.todo.data.NotesDependencyHolder
import com.dnkilic.todo.data.dateToLong
import com.dnkilic.todo.data.dateToString
import com.dnkilic.todo.data.json.Note
import com.dnkilic.todo.detail.adapter.TagAdapter
import com.dnkilic.todo.detail.dialog.DatePickerFragment
import com.dnkilic.todo.detail.dialog.SharedDateViewModel
import com.dnkilic.todo.detail.dialog.SharedTagViewModel
import com.dnkilic.todo.detail.dialog.TagInputFragment
import com.dnkilic.todo.detail.viewmodel.DetailViewModel
import com.dnkilic.todo.detail.viewmodel.DetailViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.detail_fragment.*
import java.util.*
import javax.inject.Inject


class DetailFragment : BaseFragment() {

    companion object {
        private const val EXTRA_ID = "EXTRA_ID"
        private const val EXTRA_MODE = "EXTRA_MODE"

        fun newInstance(mode: DetailMode, id: Long) = DetailFragment().apply {
            arguments = Bundle().apply {
                putLong(EXTRA_ID, id)
                putSerializable(EXTRA_MODE, mode)
            }
        }
    }

    private val component by lazy { NotesDependencyHolder.detailsComponent() }

    @Inject
    lateinit var viewModelFactory: DetailViewModelFactory

    private val viewModel: DetailViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)
    }

    private val sharedTagViewModel: SharedTagViewModel by lazy {
        ViewModelProviders.of(activity!!).get(SharedTagViewModel::class.java)
    }

    private lateinit var mode: DetailMode
    private var id: Long = -1
    private lateinit var adapter: TagAdapter
    private lateinit var rootView: View

    override fun getLayoutId() = R.layout.detail_fragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        component.inject(this)
        if (mode == DetailMode.EDIT) {
            viewModel.getNote(id)
            observeNote()
        }

        observeDetailAction()
        observeDatePickerDialog()
        observeTagInputDialog()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView = view
        arguments?.let {
            mode = it.getSerializable(EXTRA_MODE) as DetailMode
            id = it.getLong(EXTRA_ID)
        }
        adapter = TagAdapter()
        tagsRecyclerView.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_complete).isVisible = mode == DetailMode.EDIT
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_label -> showTagInputDialog()
            R.id.action_save -> saveNote()
            R.id.action_complete -> completeNote()
            R.id.action_date -> showDatePickerDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun observeDetailAction() = viewModel.operationResource.observe(this, Observer {
        when (it) {
            is Resource.Failure -> activity?.apply { finish() }
            is Resource.Success ->
                activity?.apply {
                    setResult(Activity.RESULT_OK)
                    finish()
                }
        }
    })

    private fun observeNote() = viewModel.noteResource.observe(this, Observer { result ->
        when (result) {
            is Resource.Failure -> showError(getString(R.string.error_generic))
            is Resource.Success -> initializeEditView(result.data)
        }
    })

    private fun observeDatePickerDialog() {
        ViewModelProviders.of(activity!!).get(SharedDateViewModel::class.java).time
            .observe(activity!!, Observer {
                dueDate.apply {
                    visible()
                    text = it
                }
            })
    }

    private fun observeTagInputDialog() = sharedTagViewModel.tags.observe(activity!!, Observer {
        adapter.apply {
            updateList(it)
            notifyDataSetChanged()
        }
    })

    private fun initializeEditView(note: Note) {
        if (sharedTagViewModel.tags.value == null) {
            sharedTagViewModel.tags.value = note.tags.toMutableList()
        }

        title.setText(note.title)
        description.setText(note.description)

        if (note.dueDate != -1L) {
            dueDate.apply {
                visible()
                text = dateToString(note.dueDate)
            }
        }
    }

    private fun note(id: Long? = null): Note {
        val uniqueId = id ?: UUID.randomUUID().mostSignificantBits
        val title = title.text.toString()
        val description = description.text.toString()
        val date = if (dueDate.text.isNotBlank()) {
            dateToLong(dueDate.text.toString())
        } else {
            -1
        }
        val tags = sharedTagViewModel.tags.value?.toList() ?: emptyList()
        return Note(uniqueId, title, description, date, tags,false)
    }

    private fun showTagInputDialog(): Boolean {
        val tagInputDialogFragment = TagInputFragment()
        tagInputDialogFragment.show(fragmentManager!!, null)
        return true
    }

    private fun showDatePickerDialog(): Boolean {
        val datePickerDialogFragment = DatePickerFragment()
        datePickerDialogFragment.show(fragmentManager!!, null)
        return true
    }

    private fun showError(message: String) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun validate(): Boolean {
        return !(title.text.isNullOrBlank() && description.text.isNullOrBlank())
    }

    private fun saveNote(): Boolean {
        return if (validate()) {
            when (mode) {
                DetailMode.ADD -> { viewModel.createNote(note()) }
                DetailMode.EDIT -> { viewModel.updateNote(note(id)) }
            }
            true
        } else {
            showError(getString(R.string.error_input))
            false
        }
    }

    private fun completeNote(): Boolean {
        return if (validate()) {
            viewModel.updateNote(note(id).copy(isCompleted = true))
            true
        } else {
            showError(getString(R.string.error_input))
            false
        }
    }
}
