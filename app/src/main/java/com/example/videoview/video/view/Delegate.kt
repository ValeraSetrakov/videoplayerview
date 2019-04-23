package com.example.videoview.video.view

interface Delegate {
    fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int)
    fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int)
}