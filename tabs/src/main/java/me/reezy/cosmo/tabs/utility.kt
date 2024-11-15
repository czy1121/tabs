@file:Suppress("NOTHING_TO_INLINE")

package me.reezy.cosmo.tabs

import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.forEach
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


val TabLayout.Tab.textView: TextView?
    get() = customView?.findViewById(android.R.id.text1) ?: kotlin.run {
        view.forEach {
            if (it is TextView) {
                return@run it
            }
        }
        return@run null
    }
val TabLayout.Tab.iconView: ImageView?
    get() = customView?.findViewById(android.R.id.icon1) ?: kotlin.run {
        view.forEach {
            if (it is ImageView) {
                return@run it
            }
        }
        return@run null
    }

fun TabLayout.get(name: String): TabLayout.Tab? {
    if (name.isEmpty()) return null
    (0 until tabCount).forEach { i ->
        getTabAt(i)?.let {
            if (it.tag?.equals(name) == true) {
                return it
            }
        }
    }
    return null
}

inline fun TabLayout.select(name: String): TabLayout.Tab? {
    return get(name)?.apply { select() }
}

inline fun TabLayout.select(index: Int): TabLayout.Tab? {
    return getTabAt(index)?.apply { select() }
}


fun TabLayout.setup(tabs: List<TabItem>, each: (TabItemView.() -> Unit)? = null) {
    removeAllTabs()
    for (item in tabs) {
        val view = TabItemView(context).setup(item)

        addTab(newTab().setTag(item).setCustomView(view).setText(item.text))

        view.textView?.setTextColor(tabTextColors)
        each?.invoke(view)
    }
}

fun TabLayout.setup(tabs: List<TabItem>, pager: ViewPager2, each: (TabItemView.() -> Unit)? = null) {
    TabLayoutMediator(this, pager) { tab, position ->
        val item = tabs[position]
        val view = TabItemView(context).setup(item)

        tab.tag = item
        tab.text = item.text
        tab.customView = view

        view.textView?.setTextColor(tabTextColors)
        each?.invoke(view)
    }.attach()
}

fun TabLayout.each(block: (TabLayout.Tab) -> Unit) {
    (0 until tabCount).forEach {
        getTabAt(it)?.let(block)
    }
}


//private val TabLayout.tabTextSize: Float
//    get() = kotlin.runCatching {
//        val field = TabLayout::class.java.getDeclaredField("tabTextSize")
//        field.isAccessible = true
//        field.get(this) as Float
//    }.getOrElse { 14 * resources.displayMetrics.density }