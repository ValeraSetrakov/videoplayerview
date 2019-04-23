package com.example.videoview.video.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

open class LayoutDelegate @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val delegates = mutableListOf<Delegate>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        delegates.forEach { it.onMeasure(widthMeasureSpec, heightMeasureSpec) }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        delegates.forEach { it.onLayout(changed, l, t, r, b) }
    }

    fun addDelegate(delegate: Delegate) {
        delegates.add(delegate)
        requestLayout()
    }
}