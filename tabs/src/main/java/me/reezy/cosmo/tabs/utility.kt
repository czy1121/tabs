@file:Suppress("NOTHING_TO_INLINE")

package me.reezy.cosmo.tabs

import android.widget.TextView
import androidx.core.view.forEach
import com.google.android.material.tabs.TabLayout


val TabLayout.Tab.textView: TextView?
    get() = customView?.findViewById(android.R.id.text1) ?: kotlin.run {
        view.forEach {
            if (it is TextView) {
                return@run it
            }
        }
        return@run null
    }

fun TabLayout.get(name: String): TabLayout.Tab? {
    if (name.isEmpty()) return null
    (0 until tabCount).forEach { i ->
        getTabAt(i)?.let {
            if ((it.tag as TabItem).name == name) {
                return it
            }
        }
    }
    return null
}

inline fun TabLayout.select(name: String): TabLayout.Tab? {
    return get(name)?.apply { select() }
}

fun TabLayout.setup(tabs: List<TabItem>, each: TabItemView.() -> Unit = {}) {
    removeAllTabs()
    for (item in tabs) {
        addTab(newTab().setTag(item).setCustomView(TabItemView(context).setup(item).apply(each)).setText(item.text))
    }
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