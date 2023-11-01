package com.breakapp.room.di

import com.breakapp.apv2.ui.configs.viewmodel.repository.EntryRepo
import com.breakapp.apv2.ui.configs.viewmodel.repository.EntryRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import javax.sql.DataSource


@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityModule {

    @Binds
    abstract fun bindRepoImpl(repoImpl: EntryRepoImpl): EntryRepo


    @Binds
    abstract fun bindDataSource(dataSource: DataSource) :DataSource
}