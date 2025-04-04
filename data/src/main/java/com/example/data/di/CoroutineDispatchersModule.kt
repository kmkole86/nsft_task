package com.example.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class CoroutineDispatchersModule {

    @Provides
    @DefaultDispatcher
    fun provideDefaultCoroutineScheduler(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @IoDispatcher
    fun provideIoCoroutineScheduler(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @MainImmediateDispatcher
    fun provideMainImmediateCoroutineScheduler(): CoroutineDispatcher = Dispatchers.Main.immediate
}