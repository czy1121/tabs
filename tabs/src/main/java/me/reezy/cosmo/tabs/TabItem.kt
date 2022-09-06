package me.reezy.cosmo.tabs

import androidx.annotation.Keep

@Keep
class TabItem(
    val name: String,
    val text: String? = null,
    val iconNormal: String? = null,
    val iconActive: String? = null,
    val iconSize: Int? = null,
)