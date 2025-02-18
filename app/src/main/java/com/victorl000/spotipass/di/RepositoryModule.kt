package com.victorl000.spotipass.di

import com.victorl000.spotipass.apis.repository.BleRepositoryImpl
import com.victorl000.spotipass.apis.repository.CryptoRepositoryImpl
import com.victorl000.spotipass.apis.repository.SpotifyRepositoryImpl
import com.victorl000.spotipass.domain.repository.BleRepository
import com.victorl000.spotipass.domain.repository.CryptoRepository
import com.victorl000.spotipass.domain.repository.SpotifyRepository
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

    @Binds
    @Singleton
    abstract fun bindCryptoRepository(
        cryptoRepositoryImpl: CryptoRepositoryImpl
    ) : CryptoRepository

    @Binds
    @Singleton
    abstract fun bindSpotifyRepository(
        spotifyRepositoryImpl: SpotifyRepositoryImpl
    ) : SpotifyRepository
}