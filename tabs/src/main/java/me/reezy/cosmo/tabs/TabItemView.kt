package me.reezy.cosmo.tabs

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.doOnNextLayout
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import me.reezy.cosmo.tabs.CoilImageLoader

@Suppress("NOTHING_TO_INLINE")
class TabItemView(context: Context) : LinearLayoutCompat(context) {
    companion object {
        var imageLoader: ImageLoader = CoilImageLoader()
    }


    var iconView: ImageView? = null
        private set
    var textView: TextView? = null
        private set

    private val badge by lazy { BadgeDrawable.create(context) }


    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        layoutParams = ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

    }

    @SuppressLint("UnsafeOptInUsageError")
    fun updateBadge(block: BadgeDrawable.() -> Unit) {
        doOnNextLayout {
            badge.block()
            val view = iconView ?: textView ?: return@doOnNextLayout
            BadgeUtils.attachBadgeDrawable(badge, view)
        }
    }

    inline fun setBadgeNumber(value: Int) {
        updateBadge {
            number = value
            isVisible = true
        }
    }

    inline fun setBadgeVisible(value: Boolean) {
        updateBadge {
            clearNumber()
            isVisible = value
        }
    }

    fun setup(item: TabItem): TabItemView {
        if (!item.iconNormal.isNullOrEmpty()) {
            val view = iconView ?: AppCompatImageView(context).apply {
                id = android.R.id.icon1
                adjustViewBounds = true
                layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                addView(this)
                iconView = this
            }
            val width = ((item.iconSize ?: 0) * resources.displayMetrics.density).toInt()
            val height = ((item.iconSize ?: 24) * resources.displayMetrics.density).toInt()
            view.setImageDrawable(context.createIcon(item.iconNormal, item.iconActive, width, height) {
                view.requestLayout()
            })
            view.visibility = View.VISIBLE
        } else {
            iconView?.visibility = View.GONE
        }

        if (!item.text.isNullOrEmpty()) {
            val view = textView ?: AppCompatTextView(context).apply {
                id = android.R.id.text1
                gravity = Gravity.CENTER
                addView(this)
                textView = this
            }
            view.text = item.text
            view.visibility = View.VISIBLE
        } else {
            textView?.visibility = View.GONE
        }
        return this
    }


    private fun Context.createIcon(iconNormal: String?, iconActive: String?, width: Int, height: Int, onLoaded: () -> Unit): Drawable? {
        if (iconNormal.isNullOrEmpty()) return null
        if (iconActive.isNullOrEmpty()) return createDrawable(iconNormal, width, height, onLoaded)
        return StateListDrawable().apply {
            addState(intArrayOf(android.R.attr.state_selected), createDrawable(iconActive, width, height, onLoaded))
            addState(intArrayOf(), createDrawable(iconNormal, width, height, onLoaded))
        }
    }


    private fun Context.createDrawable(url: String?, width: Int, height: Int, onLoaded: () -> Unit): Drawable {
 
        val wrapper = DrawableWrapper(ColorDrawable())
        imageLoader.load(applicationContext, url) {
            wrapper.wrappedDrawable = it
            if (width > 0 && height > 0) {
                wrapper.setBounds(0, 0, width, height)
            } else if (width > 0) {
                wrapper.setBounds(0, 0, width, (width * it.intrinsicHeight / it.intrinsicWidth.toFloat()).toInt())
            } else {
                wrapper.setBounds(0, 0, (height * it.intrinsicWidth / it.intrinsicHeight.toFloat()).toInt(), height)
            }
            wrapper.invalidateSelf()
            onLoaded()
        }
        return wrapper
    }
}
