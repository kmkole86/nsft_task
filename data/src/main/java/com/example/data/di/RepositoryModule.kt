package com.example.data.di

import com.example.data.api.data_source_impl.GitRepoRemoteDataSource
import com.example.data.database.data_source_impl.GitRepoLocalDataSource
import com.example.data.repository_impl.GitRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideGitRepositoryImpl(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        localDataSource: GitRepoLocalDataSource, remoteDataSource: GitRepoRemoteDataSource
    ): GitRepositoryImpl {
        return GitRepositoryImpl(
            dispatcher = dispatcher,
            localDatasource = localDataSource,
            remoteDataSource = remoteDataSource
        )
    }
}