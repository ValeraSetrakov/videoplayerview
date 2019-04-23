package com.example.videoview.video.view

class Delegates (private val delegates: MutableList<Delegate> = mutableListOf()): Delegate {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        delegates.forEach { it.onMeasure(widthMeasureSpec, heightMeasureSpec) }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        delegates.forEach { it.onLayout(changed, left, top, right, bottom) }
    }

    fun addDelegate(delegate: Delegate) {
        if (delegates.contains(delegate))
            return
        delegates.add(delegate)
    }

    fun removeDelegate(delegate: Delegate) {
        if (!delegates.contains(delegate))
            return
        delegates.remove(delegate)
    }
}