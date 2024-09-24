package com.example.weatherforecasts.di

import com.example.weatherforecast.repository.repositoryImplementation.RetrofitWeatherRepository
import com.example.weatherforecasts.repository.WeatherDataRepository
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