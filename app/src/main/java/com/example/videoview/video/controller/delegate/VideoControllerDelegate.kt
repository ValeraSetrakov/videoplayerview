package com.example.videoview.video.controller.delegate

import com.example.videoview.video.VideoPlayer
import com.example.videoview.video.controller.VideoControllerView
import com.example.videoview.video.view.SimpleDelegate

open class VideoControllerDelegate(parent: VideoControllerView):
    SimpleDelegate<VideoControllerView>(parent = parent) {
    protected val videoPlayer: VideoPlayer = parent.videoPlayer
}