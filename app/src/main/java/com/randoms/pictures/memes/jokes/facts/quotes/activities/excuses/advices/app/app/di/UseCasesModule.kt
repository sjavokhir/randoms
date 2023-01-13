package com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data.repository.RandomRepository
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.domain.RandomUseCases

@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {

    @[Provides ViewModelScoped]
    fun providesRandomUseCases(repository: RandomRepository): RandomUseCases {
        return RandomUseCases(
            getRandom = repository::getRandom,
            getRandomList = repository::getRandomList,
            getRandomStringList = repository::getRandomStringList,
        )
    }
}
