package me.reezy.cosmo.tabs

import androidx.annotation.Keep

class TabItem(
    @Keep val name: String,
    @Keep val text: String? = null,
    @Keep val iconNormal: String? = null,
    @Keep val iconActive: String? = null,
    @Keep val iconSize: Int? = null,
)