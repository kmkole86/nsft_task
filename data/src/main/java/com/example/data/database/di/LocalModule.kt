package com.example.data.database.di

import android.app.Application
import androidx.room.Room
import com.example.data.database.GitRepoDatabase
import com.example.data.database.GitRepoDatabase.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideDatabase(
        context: Application
    ) = Room.databaseBuilder(
        context,
        GitRepoDatabase::class.java,
        DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideGitRepoDao(database: GitRepoDatabase) = database.gitRepositoriesDao()
}