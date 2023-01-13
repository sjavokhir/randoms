package com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.app.di

import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data.network.RandomsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @[Provides Singleton]
    fun provideRandomsApi(retrofit: Retrofit): RandomsApi = retrofit.create(RandomsApi::class.java)
}
