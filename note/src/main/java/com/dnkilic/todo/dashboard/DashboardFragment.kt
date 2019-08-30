package com.dnkilic.todo.dashboard

import android.os.Bundle
import com.dnkilic.todo.R
import com.dnkilic.todo.core.base.BaseFragment
import com.dnkilic.todo.data.NotesDependencyHolder

class DashboardFragment : BaseFragment() {

    override fun getLayoutId() = R.layout.dashboard_fragment

    private val component by lazy { NotesDependencyHolder.dashboardComponent() }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        component.inject(this)
    }
}
