package com.example.videoview.video.controller

import android.content.Context
import android.util.AttributeSet
import com.example.videoview.video.VideoPlayer
import com.example.videoview.video.view.LayoutDelegate

abstract class VideoControllerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LayoutDelegate(context, attrs, defStyleAttr) {
    var videoPlayer = VideoPlayer()
}
