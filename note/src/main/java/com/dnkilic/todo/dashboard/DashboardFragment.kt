package com.dnkilic.todo.dashboard

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import com.dnkilic.todo.R
import com.dnkilic.todo.core.base.BaseFragment
import com.dnkilic.todo.dashboard.adapter.DashboardAdapter
import com.dnkilic.todo.dashboard.adapter.DashboardItemsLookup
import com.dnkilic.todo.dashboard.viewmodel.DashboardViewModel
import com.dnkilic.todo.dashboard.viewmodel.DashboardViewModelFactory
import com.dnkilic.todo.data.NotesDependencyHolder
import com.dnkilic.todo.data.json.Note
import kotlinx.android.synthetic.main.dashboard_fragment.*
import javax.inject.Inject


class DashboardFragment : BaseFragment(), ActionMode.Callback {

    override fun getLayoutId() = R.layout.dashboard_fragment

    private val component by lazy { NotesDependencyHolder.dashboardComponent() }

    @Inject
    lateinit var viewModelFactory: DashboardViewModelFactory

    private val viewModel: DashboardViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(DashboardViewModel::class.java)
    }

    private lateinit var adapter: DashboardAdapter
    private lateinit var tracker: SelectionTracker<Long>
    private var actionMode: ActionMode? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        component.inject(this)
        viewModel.getNotes()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createNote.setOnClickListener {
            // TODO show detail
        }
        adapter = DashboardAdapter {
            // TODO show detail
        }
        tasksRecyclerView.adapter = adapter

        initMultiSelectionTracker().addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()
                val selectedItemCount = tracker.selection.size()
                if (selectedItemCount > 0) {
                    showActions()
                } else {
                    hideActions()
                }
            }
        })

        adapter.tracker = tracker
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu, menu)
        val searchView = SearchView((activity as DashboardActivity).supportActionBar?.themedContext)
        menu.findItem(R.id.action_search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }

        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // TODO search
                return false
            }
        })
    }

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> {
                // TODO delete
                mode.finish()
                return true
            }
            R.id.action_complete -> {
                // TODO complete
                mode.finish()
                return true
            }
        }
        return false
    }

    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        val inflater = mode.menuInflater
        inflater?.inflate(R.menu.dashboard_action_menu, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
        menu.apply {
            findItem(R.id.action_delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            findItem(R.id.action_complete).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        }
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        actionMode = null
        tracker.clearSelection()
    }

    private fun hideActions() {
        actionMode?.finish()
    }

    private fun showActions() {
        if (actionMode == null) {
            actionMode = (activity as AppCompatActivity).startSupportActionMode(this@DashboardFragment)
        }
    }

    private fun initMultiSelectionTracker(): SelectionTracker<Long> {
        tracker = SelectionTracker.Builder<Long>(
            SELECTION_ID, tasksRecyclerView,
            StableIdKeyProvider(tasksRecyclerView),
            DashboardItemsLookup(tasksRecyclerView),
            StorageStrategy.createLongStorage())
            .withSelectionPredicate(SelectionPredicates.createSelectAnything())
            .build()

        return tracker
    }

    private companion object {
        private const val SELECTION_ID = "SELECTION_ID"
    }
}
