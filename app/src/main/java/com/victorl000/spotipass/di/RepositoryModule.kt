package com.victorl000.spotipass.di

import com.victorl000.spotipass.apis.repository.BleRepositoryImpl
import com.victorl000.spotipass.domain.repository.BleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBleRepository(
        bleRepositoryImpl: BleRepositoryImpl
    ) : BleRepository
}