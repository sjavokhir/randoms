package com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.core.helpers

import kotlinx.coroutines.Dispatchers

object DispatcherProvider {
    val Main get() = Dispatchers.Main
    val IO get() = Dispatchers.IO
    val Default get() = Dispatchers.Default
}
