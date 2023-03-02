package com.whatever_packagename_you_decide


import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout

import com.harshal.financemonitor.R //// Todo Replace this with your package name
import com.harshal.financemonitor.domain.invisible //// Todo Replace this with your extension method 
import com.harshal.financemonitor.domain.visible //// Todo Replace this with your extension method 


class TabLayoutHelper(
    private val tabLayout: TabLayout,
    val colorSelected: Int,
    val colorNormal: Int,
    val font: Typeface,
    val fontSize:Float,
    private val hasBadge: Boolean = false
) {

    private lateinit var action: (id: Int) -> Unit

    init {
        val view = tabLayout.getChildAt(0) as LinearLayout
        view.apply {
            showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
            val drawable = GradientDrawable()
            drawable.setColor(colorSelected)
            drawable.setSize(2, 1)
            dividerPadding = 10
            dividerDrawable = drawable

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    updateTabStyle(tab, colorSelected, Typeface.BOLD)
                    action(tab.id)
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    updateTabStyle(tab, colorNormal, Typeface.NORMAL)
                }

                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }
    }

    fun setupTabWithActions(tabs: List<Pair<String, Int>>, action: (id: Int) -> Unit) {
        clear()
        this.action = action
        tabs.forEachIndexed { index, p ->
            val newTab = tabLayout.newTab()
            if (hasBadge) {
                newTab.setCustomView(R.layout.cutom_tab)
                newTab.customView?.apply { findViewById<TextView>(R.id.titleView).text = p.first }
            } else {
                newTab.text = p.first
            }
            newTab.id = p.second
            tabLayout.addTab(
                newTab, index, index == 0
            )
//  since we are adding tabs after initialization of TabLayoutHelper, we manually have to update tab style first
            updateTabStyle(
                newTab,
                if (index != 0) colorNormal else colorSelected,
                if (index != 0) Typeface.NORMAL else Typeface.BOLD
            )
        }
    }

    fun setBadgeValue(tabPosition: Int, badgeValue: String?) {
        val view: View = tabLayout.getTabAt(tabPosition)?.customView ?: return
        val badgeView = view.findViewById<View>(R.id.badgeView) as TextView
        badgeView.text = badgeValue
        if (badgeValue == null || badgeValue.isEmpty()) {
            badgeView.invisible()
        } else {
            badgeView.visible()
        }
    }

    private fun updateTabStyle(tab: TabLayout.Tab, textColor: Int, style: Int) {
        if (!hasBadge) {
            tab.view.apply {
                val tabTextView = tab.view.getChildAt(1) as TextView
                val myTextSize = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_SP, fontSize, resources.displayMetrics
                )
                tabTextView.textSize = myTextSize
                tabTextView.setTextColor(ContextCompat.getColor(context, textColor))
                tabTextView.setTypeface(font, style)
            }
        } else {
            tab.customView?.apply {
                val tabTextView = findViewById<TextView>(R.id.titleView)
                tabTextView.setTextColor(ContextCompat.getColor(context, textColor))
            }
        }
    }

    private fun clear() {
        action = { }
        tabLayout.removeAllTabs()
    }
}
