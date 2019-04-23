package com.example.videoview.video.view

import android.view.View
import android.view.ViewGroup

open class SimpleDelegate<T: ViewGroup>(protected val parent: T): Delegate {
    fun addView(view: View) {
        parent.addView(view)
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

    }
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {

    }
}