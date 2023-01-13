package com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.core.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.MobileAds

fun Context.adsInitialize() {
    MobileAds.initialize(this)
}

inline val Context.inflater: LayoutInflater
    get() = LayoutInflater.from(this)

fun <T> Fragment.start(it: Class<T>) {
    val intent = Intent(requireActivity(), it)
    startActivity(intent)
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.shareText(text: String) {
    try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(intent, null))
    } catch (t: Throwable) {
        t.printStackTrace()
    }
}