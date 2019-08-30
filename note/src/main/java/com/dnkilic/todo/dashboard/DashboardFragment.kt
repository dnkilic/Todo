package com.dnkilic.todo.dashboard

import android.os.Bundle
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
        component.inject(this)
        viewModel.getNotes()
    }
}
