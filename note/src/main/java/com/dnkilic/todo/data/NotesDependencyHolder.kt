package com.dnkilic.todo.data

import com.dnkilic.todo.core.application.TodoApplication
import com.dnkilic.todo.dashboard.di.DaggerDashboardComponent
import com.dnkilic.todo.dashboard.di.DashboardComponent

object NotesDependencyHolder {
    private var dashboardComponent: DashboardComponent? = null

    fun dashboardComponent(): DashboardComponent {
        if (dashboardComponent == null)
            dashboardComponent = DaggerDashboardComponent.builder().coreComponent(TodoApplication.coreComponent).build()
        return dashboardComponent as DashboardComponent
    }

    fun destroy() {
        dashboardComponent = null
    }
}