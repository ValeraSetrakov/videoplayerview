package com.example.videoview.video.videocontrollerview

import android.content.Context
import android.util.AttributeSet
import com.example.videoview.video.videoview.videoplayer.VideoPlayer
import com.example.videoview.video.view.LayoutDelegate

abstract class VideoControllerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LayoutDelegate(context, attrs, defStyleAttr) {
    var videoPlayer = VideoPlayer()
}
