package com.dnkilic.todo.dashboard.di

import android.content.Context
import androidx.room.Room
import com.dnkilic.todo.core.di.CoreComponent
import com.dnkilic.todo.dashboard.DashboardFragment
import com.dnkilic.todo.dashboard.model.DashboardContract
import com.dnkilic.todo.dashboard.model.DashboardLocalData
import com.dnkilic.todo.dashboard.model.DashboardRepository
import com.dnkilic.todo.dashboard.viewmodel.DashboardViewModelFactory
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
    fun dashboardViewModelFactory(repository: DashboardContract.Repository) = DashboardViewModelFactory(repository)

    @Provides
    @DashboardScope
    fun dashboardRepository(local: DashboardContract.Local): DashboardContract.Repository  = DashboardRepository(local)

    @Provides
    @DashboardScope
    fun localData(noteDatabase: NoteDatabase): DashboardContract.Local = DashboardLocalData(noteDatabase)

    @Provides
    @DashboardScope
    fun noteDatabase(context: Context) = Room.databaseBuilder(context, NoteDatabase::class.java, "note_db").build()
}