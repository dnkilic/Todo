package com.dnkilic.todo.core.application

import android.app.Application
import com.dnkilic.todo.core.di.AppModule
import com.dnkilic.todo.core.di.CoreComponent
import com.dnkilic.todo.core.di.DaggerCoreComponent

class TodoApplication : Application() {

    companion object {
        lateinit var coreComponent: CoreComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDependencyInjection()
    }

    private fun initDependencyInjection() {
        coreComponent = DaggerCoreComponent.builder().appModule(AppModule(this)).build()
    }

}