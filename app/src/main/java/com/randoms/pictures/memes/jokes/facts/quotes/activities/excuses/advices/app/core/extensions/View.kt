package com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.core.extensions

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.core.helpers.ThrottledOnClickListener

fun View.onClick(onClickAction: () -> Unit) {
    setOnClickListener(ThrottledOnClickListener(onClickAction))
}

fun View.beVisibleIf(isVisible: Boolean) = if (isVisible) beVisible() else beGone()

fun View.beVisible() {
    isVisible = true
}

fun View.beGone() {
    isVisible = false
}