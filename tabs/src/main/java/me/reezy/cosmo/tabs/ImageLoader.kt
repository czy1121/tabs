package me.reezy.cosmo.tabs

import android.content.Context
import android.graphics.drawable.Drawable

interface ImageLoader {
    fun load(context: Context, url: String?, onSuccess: (drawable: Drawable) -> Unit)
}