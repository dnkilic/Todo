package com.dnkilic.todo.core.application

import android.app.Application
import com.dnkilic.todo.core.BuildConfig
import com.dnkilic.todo.core.di.AppModule
import com.dnkilic.todo.core.di.CoreComponent
import com.dnkilic.todo.core.di.DaggerCoreComponent
import timber.log.Timber

class TodoApplication : Application() {

    companion object {
        lateinit var coreComponent: CoreComponent
    }

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initDependencyInjection()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initDependencyInjection() {
        coreComponent = DaggerCoreComponent.builder().appModule(AppModule(this)).build()
    }

}