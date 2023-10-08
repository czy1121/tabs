package me.reezy.cosmo.tabs

import android.content.Context
import android.graphics.drawable.Drawable
import coil.Coil
import coil.request.ImageRequest
import me.reezy.cosmo.tabs.ImageLoader

class CoilImageLoader : ImageLoader {
    override fun load(context: Context, url: String?, onSuccess: (drawable: Drawable) -> Unit) {
        Coil.imageLoader(context).enqueue(ImageRequest.Builder(context).data(url).allowHardware(false).target(onSuccess = onSuccess).build())
    }
}