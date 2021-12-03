package com.jaydeepbhayani.ilabank.ui.common.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jaydeepbhayani.ilabank.R

/**
 * * [ScrollChildSwipeRefreshLayout]
 *
 * Custom class created for custom [SwipeRefreshLayout] using [SwipeRefreshLayout].
 * @author
 * created by Jaydeep Bhayani on 03/12/2021
 */

class ScrollChildSwipeRefreshLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : SwipeRefreshLayout(context, attrs) {

    var scrollUpChild: View? = null

    override fun canChildScrollUp() =
        scrollUpChild?.canScrollVertically(-1) ?: super.canChildScrollUp()

}

fun ScrollChildSwipeRefreshLayout.refresh() {
    findViewById<ScrollChildSwipeRefreshLayout>(this.id).apply {
        setColorSchemeColors(
            ContextCompat.getColor(context, R.color.colorBlue),
            ContextCompat.getColor(context, R.color.colorAccent),
            ContextCompat.getColor(context, R.color.colorPrimaryDark)
        )
    }
}