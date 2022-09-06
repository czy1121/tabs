package me.reezy.cosmo.tabs.ontabselected

import android.animation.ValueAnimator
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import me.reezy.cosmo.tabs.textView


class OnTabTextSize(private val normal: Float, private val active: Float) : TabLayout.OnTabSelectedListener {

    override fun onTabSelected(tab: TabLayout.Tab) {
        tab.textView?.animateTextSize(normal, active)
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {
        tab.textView?.animateTextSize(active, normal)
    }

    override fun onTabReselected(tab: TabLayout.Tab) {
    }

    private fun TextView.animateTextSize(from: Float, to: Float) {
        val animator = ValueAnimator.ofFloat(from, to)
        animator.addUpdateListener {
            textSize = it.animatedValue as Float
        }
        animator.start()
    }
}