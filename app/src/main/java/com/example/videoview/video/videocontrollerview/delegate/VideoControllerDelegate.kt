package com.example.videoview.video.videocontrollerview.delegate

import com.example.videoview.video.videoview.videoplayer.VideoPlayer
import com.example.videoview.video.videocontrollerview.VideoControllerView
import com.example.videoview.video.view.SimpleDelegate

open class VideoControllerDelegate(parent: VideoControllerView):
    SimpleDelegate<VideoControllerView>(parent = parent) {
    protected val videoPlayer: VideoPlayer = parent.videoPlayer
}