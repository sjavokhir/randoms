package com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.core.extensions

import android.content.Context
import android.util.TypedValue
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun Context.primaryColor(): Int {
    val value = TypedValue()
    theme.resolveAttribute(com.google.android.material.R.attr.colorPrimary, value, true)
    return value.data
}

fun ImageView.loadImage(
    url: String?,
    withProgress: Boolean = true,
    skipMemoryCache: Boolean = false
) {
    val progressDrawable = CircularProgressDrawable(context)
    progressDrawable.strokeWidth = 10f
    progressDrawable.centerRadius = 48f
    progressDrawable.setColorSchemeColors(context.primaryColor())
    progressDrawable.start()

    Glide.with(this)
        .load(url)
        .diskCacheStrategy(if (skipMemoryCache) DiskCacheStrategy.NONE else DiskCacheStrategy.ALL)
        .skipMemoryCache(skipMemoryCache)
        .transition(DrawableTransitionOptions.withCrossFade())
        .placeholder(if (withProgress) progressDrawable else null)
        .into(this)
}