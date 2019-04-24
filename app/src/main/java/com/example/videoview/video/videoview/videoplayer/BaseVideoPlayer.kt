package com.example.videoview.video.videoview.videoplayer

import android.media.MediaPlayer

open class BaseVideoPlayer: MediaPlayer() {

    protected val onStartVideoPlayerListeners =
        OnStartVideoPlayerListeners()
    protected var onStartPrepareVideoPlayerListeners =
        OnStartPrepareVideoPlayerListeners()
    protected var onPauseVideoPlayerListeners =
        OnPauseVideoPlayerListeners()
    protected val onPrepareListeners = OnPrepareListeners().also {
        super.setOnPreparedListener(it)
    }
    protected val onCompletionListeners = OnCompletionListeners()
        .also {
        super.setOnCompletionListener(it)
    }
    protected val onSeekCompleteListeners = OnSeekCompleteListeners()
        .also {
        super.setOnSeekCompleteListener(it)
    }

    fun addOnStartVideoPlayerListener (listener: OnStartVideoPlayerListener) {
        onStartVideoPlayerListeners.listeners.add(listener)
    }

    fun addOnStartPrepareVideoPlayerListener (listener: OnStartPrepareVideoPlayerListener) {
        onStartPrepareVideoPlayerListeners.listeners.add(listener)
    }

    fun addOnPauseVideoPlayerListener (listener: OnPauseVideoPlayerListener) {
        onPauseVideoPlayerListeners.listeners.add(listener)
    }

    override fun setOnCompletionListener(listener: OnCompletionListener?) {
        listener ?: return
        onCompletionListeners.listeners.add(listener)
    }

    override fun setOnPreparedListener(listener: OnPreparedListener?) {
        listener ?: return
        onPrepareListeners.listeners.add(listener)
    }

    override fun setOnSeekCompleteListener(listener: OnSeekCompleteListener?) {
        listener ?: return
        onSeekCompleteListeners.listeners.add(listener)

    }












    interface OnStartVideoPlayerListener {
        fun onStartVideoPlayer(vp: VideoPlayer)
    }

    interface OnPauseVideoPlayerListener {
        fun onPauseVideoPlayer(vp: VideoPlayer)
    }

    interface OnStartPrepareVideoPlayerListener {
        fun onStartPrepareVideoPlayerListener(vp: VideoPlayer)
    }

    class OnPrepareListeners: OnPreparedListener {
        val listeners = mutableListOf<OnPreparedListener>()
        override fun onPrepared(mp: MediaPlayer?) {
            listeners.forEach { it.onPrepared(mp) }
        }
    }

    class OnCompletionListeners: OnCompletionListener {
        val listeners = mutableListOf<OnCompletionListener>()
        override fun onCompletion(mp: MediaPlayer?) {
            listeners.forEach { it.onCompletion(mp) }
        }
    }

    class OnStartVideoPlayerListeners:
        OnStartVideoPlayerListener {
        val listeners = mutableListOf<OnStartVideoPlayerListener>()
        override fun onStartVideoPlayer(vp: VideoPlayer) {
            listeners.forEach { it.onStartVideoPlayer(vp) }
        }
    }

    class OnStartPrepareVideoPlayerListeners:
        OnStartPrepareVideoPlayerListener {
        val listeners = mutableListOf<OnStartPrepareVideoPlayerListener>()
        override fun onStartPrepareVideoPlayerListener(vp: VideoPlayer) {
            listeners.forEach{ it.onStartPrepareVideoPlayerListener(vp) }
        }
    }

    class OnPauseVideoPlayerListeners:
        OnPauseVideoPlayerListener {
        val listeners = mutableListOf<OnPauseVideoPlayerListener>()
        override fun onPauseVideoPlayer(vp: VideoPlayer) {
            listeners.forEach{ it.onPauseVideoPlayer(vp) }
        }
    }

    class OnSeekCompleteListeners: OnSeekCompleteListener {
        val listeners = mutableListOf<OnSeekCompleteListener>()
        override fun onSeekComplete(mp: MediaPlayer?) {
            listeners.forEach{ it.onSeekComplete(mp) }
        }
    }
}