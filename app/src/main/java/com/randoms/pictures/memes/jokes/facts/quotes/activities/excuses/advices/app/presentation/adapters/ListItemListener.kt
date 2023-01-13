package com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.presentation.adapters

import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data.model.ListModel

interface ListItemListener {
    fun onItemClick(model: ListModel)
}