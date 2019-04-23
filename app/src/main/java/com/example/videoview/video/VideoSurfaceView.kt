package com.example.videoview.video

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

class VideoSurfaceView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr) {

    var videoWidth: Int = 0
    var videoHeight: Int = 0

    /** simple implementation of surface holder callback */
    abstract class SimpleCallback: SurfaceHolder.Callback {
        override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {}
        override fun surfaceDestroyed(holder: SurfaceHolder?) {}
        override fun surfaceCreated(holder: SurfaceHolder?) {}
    }

//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        if (videoWidth == 0 || videoHeight == 0)
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        else
//            setMeasuredDimension(videoWidth, videoHeight)
//    }
}