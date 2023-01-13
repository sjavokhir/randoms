package com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data.base

interface DataMapper<T, S> {
    fun T.mapToDomain(): S
}

fun <T : DataMapper<T, S>, S> T.mapToDomain(): S = this.mapToDomain()
