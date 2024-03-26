package me.reezy.cosmo.tabs

import androidx.annotation.Keep

class TabItem(
    @Keep val name: String,
    @Keep val text: String? = null,
    @Keep val iconNormal: String? = null,
    @Keep val iconActive: String? = null,
    @Keep val iconSize: Int? = null,
    @Keep val iconResId: Int = 0,
) {
    val hasIcon: Boolean get() = !iconNormal.isNullOrEmpty() || iconResId > 0

    override fun equals(other: Any?): Boolean {
        return if (other is String) name == other else super.equals(other)
    }
}