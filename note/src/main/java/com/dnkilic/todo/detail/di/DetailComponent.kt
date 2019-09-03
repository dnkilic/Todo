package com.dnkilic.todo.detail.di

import com.dnkilic.todo.dashboard.di.DashboardComponent
import com.dnkilic.todo.data.local.NoteDatabase
import com.dnkilic.todo.detail.DetailFragment
import com.dnkilic.todo.detail.model.DetailsContract
import com.dnkilic.todo.detail.model.DetailsLocalData
import com.dnkilic.todo.detail.model.DetailsRepository
import com.dnkilic.todo.detail.viewmodel.DetailViewModelFactory
import dagger.Component
import dagger.Module
import dagger.Provides

@DetailScope
@Component(dependencies = [DashboardComponent::class], modules = [DetailModule::class])
interface DetailsComponent {
    fun inject(detailFragment: DetailFragment)
}

@Module
class DetailModule {

    @Provides
    @DetailScope
    fun detailViewModelFactory(repository: DetailsContract.Repository) = DetailViewModelFactory(repository)

    @Provides
    @DetailScope
    fun detailRepository(local: DetailsContract.Local): DetailsContract.Repository  = DetailsRepository(local)

    @Provides
    @DetailScope
    fun localData(noteDatabase: NoteDatabase): DetailsContract.Local = DetailsLocalData(noteDatabase)
}