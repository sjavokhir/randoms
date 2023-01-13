package com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data.repository

import kotlinx.coroutines.flow.Flow
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data.UIState
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data.base.BaseRepository
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data.model.RandomModel
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data.network.RandomsApi
import javax.inject.Inject

interface RandomRepository {

    suspend fun getRandom(baseUrl: String): Flow<UIState<RandomModel>>

    suspend fun getRandomList(baseUrl: String): Flow<UIState<RandomModel>>

    suspend fun getRandomStringList(baseUrl: String): Flow<UIState<RandomModel>>
}

class RandomRepositoryImpl @Inject constructor(
    private val randomsApi: RandomsApi
) : BaseRepository(), RandomRepository {

    override suspend fun getRandom(baseUrl: String): Flow<UIState<RandomModel>> {
        return doNetworkRequest { randomsApi.getRandom(baseUrl) }
    }

    override suspend fun getRandomList(baseUrl: String): Flow<UIState<RandomModel>> {
        return doNetworkRequestList { randomsApi.getRandomList(baseUrl) }
    }

    override suspend fun getRandomStringList(baseUrl: String): Flow<UIState<RandomModel>> {
        return doNetworkRequestStringList { randomsApi.getRandomStringList(baseUrl) }
    }
}
