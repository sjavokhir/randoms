package com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data.repository.RandomRepository
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data.repository.RandomRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @[Binds]
    fun bindRandomRepository(impl: RandomRepositoryImpl): RandomRepository
}
