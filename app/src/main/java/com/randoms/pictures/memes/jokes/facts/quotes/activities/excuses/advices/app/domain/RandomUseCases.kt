package com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.domain

import kotlinx.coroutines.flow.Flow
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data.UIState
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data.model.RandomModel

fun interface GetRandom : suspend (String) -> Flow<UIState<RandomModel>>
fun interface GetRandomList : suspend (String) -> Flow<UIState<RandomModel>>
fun interface GetRandomStringList : suspend (String) -> Flow<UIState<RandomModel>>

data class RandomUseCases(
    val getRandom: GetRandom,
    val getRandomList: GetRandomList,
    val getRandomStringList: GetRandomStringList
)
