package com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data.model

data class ListModel(
    val id: Int? = null,
    val title: String? = null,
    val cover: String? = null,
    val baseUrl: String? = null,
    val isImageUrl: Boolean = false,
    val isListUrl: Boolean = false,
    val isStringListUrl: Boolean = false,
    val isProgramming: Boolean = false,
    val type: RandomType = RandomType.MORE
)
