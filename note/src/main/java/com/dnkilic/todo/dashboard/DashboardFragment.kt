package com.dnkilic.todo.dashboard

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import com.dnkilic.todo.R
import com.dnkilic.todo.core.base.BaseFragment
import com.dnkilic.todo.dashboard.viewmodel.DashboardViewModel
import com.dnkilic.todo.dashboard.viewmodel.DashboardViewModelFactory
import com.dnkilic.todo.data.NotesDependencyHolder
import javax.inject.Inject


class DashboardFragment : BaseFragment() {

    override fun getLayoutId() = R.layout.dashboard_fragment

    private val component by lazy { NotesDependencyHolder.dashboardComponent() }

    @Inject
    lateinit var viewModelFactory: DashboardViewModelFactory

    private val viewModel: DashboardViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(DashboardViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        component.inject(this)
        viewModel.getNotes()
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
                return false
            }
        })
    }
}
