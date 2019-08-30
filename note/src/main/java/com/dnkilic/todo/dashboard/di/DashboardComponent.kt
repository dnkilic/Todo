package com.dnkilic.todo.dashboard.di

import android.content.Context
import androidx.room.Room
import com.dnkilic.todo.core.di.CoreComponent
import com.dnkilic.todo.dashboard.DashboardFragment
import com.dnkilic.todo.data.local.NoteDatabase
import dagger.Component
import dagger.Module
import dagger.Provides

@DashboardScope
@Component(dependencies = [CoreComponent::class], modules = [DashboardModule::class])
interface DashboardComponent {

    fun noteDatabase(): NoteDatabase

    fun inject(dashboardFragment: DashboardFragment)
}

@Module
class DashboardModule {

    @Provides
    @DashboardScope
    fun noteDatabase(context: Context) = Room.databaseBuilder(context, NoteDatabase::class.java, "note_db").build()
}