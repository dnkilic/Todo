package com.dnkilic.todo.data

import com.dnkilic.todo.core.application.TodoApplication
import com.dnkilic.todo.dashboard.di.DaggerDashboardComponent
import com.dnkilic.todo.dashboard.di.DashboardComponent
import com.dnkilic.todo.detail.di.DaggerDetailsComponent
import com.dnkilic.todo.detail.di.DetailsComponent

object NotesDependencyHolder {
    private var dashboardComponent: DashboardComponent? = null

    fun dashboardComponent(): DashboardComponent {
        if (dashboardComponent == null)
            dashboardComponent = DaggerDashboardComponent.builder().coreComponent(TodoApplication.coreComponent).build()
        return dashboardComponent as DashboardComponent
    }

    fun destroyDashboardComponent() {
        dashboardComponent = null
    }

    private var detailsComponent: DetailsComponent? = null

    fun detailsComponent(): DetailsComponent {
        if (detailsComponent == null)
            detailsComponent = DaggerDetailsComponent.builder().dashboardComponent(dashboardComponent()).build()
        return detailsComponent as DetailsComponent
    }

    fun destroyDetailsComponent() {
        detailsComponent = null
    }
}