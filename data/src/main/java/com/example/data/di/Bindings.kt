package com.example.data.di

import com.example.data.api.data_source_impl.GitRepoRemoteDataSource
import com.example.data.api.data_source_impl.GitRepoRemoteDataSourceImpl
import com.example.data.database.data_source_impl.GitRepoLocalDataSource
import com.example.data.database.data_source_impl.GitRepoLocalDataSourceImpl
import com.example.data.repository_impl.GitRepositoryImpl
import com.example.domain.repository.GitRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindingsModule {

    @Binds
    abstract fun bindGitRepository(
        repo: GitRepositoryImpl
    ): GitRepository

    @Binds
    abstract fun bindGitRepoRemoteDataSource(
        repo: GitRepoRemoteDataSourceImpl
    ): GitRepoRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindGitRepoLocalDataSource(dataSource: GitRepoLocalDataSourceImpl): GitRepoLocalDataSource
}