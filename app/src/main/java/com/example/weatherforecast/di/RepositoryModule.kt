package com.example.weatherforecast.di

import com.example.weatherforecast.repository.RetrofitWeatherRepository
import com.example.weatherforecast.repository.WeatherDataRepository
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
    abstract fun bindWeatherDataRepository(
        weatherRepository: RetrofitWeatherRepository
    ): WeatherDataRepository
}