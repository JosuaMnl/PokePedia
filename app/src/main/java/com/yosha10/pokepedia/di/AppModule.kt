package com.yosha10.pokepedia.di

import com.yosha10.pokepedia.data.remote.ApiService
import com.yosha10.pokepedia.repository.PokePediaRepository
import com.yosha10.pokepedia.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePokePediaRepository(api: ApiService) =
        PokePediaRepository(api)

    @Singleton
    @Provides
    fun provideApiService(): ApiService =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiService::class.java)
}