package com.example.videoview.video.videoview

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.widget.FrameLayout
import com.example.videoview.video.videoview.videoplayer.VideoPlayer


private val TAG = VideoView::class.java.simpleName

class VideoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr){

    lateinit var videoSurfaceView: VideoSurfaceView
    lateinit var videoPlayer: VideoPlayer

    /** Prepare video, create display fo him, resize display by video size */
    fun playVideo(video: String) {
        if (!::videoPlayer.isInitialized)
            videoPlayer = VideoPlayer()
        prepareDisplay(object: VideoSurfaceView.SimpleCallback(){
            override fun surfaceCreated(holder: SurfaceHolder?) {
                super.surfaceCreated(holder)
                videoPlayer.run {
                    reset()
                    setDisplay(holder)
                    setDataSource(video)
                    setOnVideoSizeChangedListener { _, width, height ->
                        setVideoSize(width, height)
                    }
                    prepareAsync()
                }
            }
        })
    }

    private fun prepareDisplay (callback: VideoSurfaceView.SimpleCallback) {
        if (::videoSurfaceView.isInitialized) {
            videoSurfaceView.holder.surface.release()
            removeView(videoSurfaceView)
        }
        this.videoSurfaceView = createDisplay(callback)
    }

    private fun createDisplay(callback: VideoSurfaceView.SimpleCallback): VideoSurfaceView {
        return VideoSurfaceView(context).apply {
            holder.addCallback(callback)
        }.also {
            addView(it)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (::videoSurfaceView.isInitialized) {
            val parentWidth = MeasureSpec.getSize(widthMeasureSpec)
            val parentHeight = MeasureSpec.getSize(heightMeasureSpec)
            var videoSurfaceWidth = videoSurfaceView.videoWidth.toFloat()
            var videoSurfaceHeight = videoSurfaceView.videoHeight.toFloat()
            if (videoSurfaceWidth > 0 && videoSurfaceHeight > 0) {
                var ratio: Float
                if (videoSurfaceWidth > videoSurfaceHeight) {
                    ratio = videoSurfaceWidth / parentWidth.toFloat()
                    videoSurfaceWidth /= ratio
                    videoSurfaceHeight /= ratio
                } else if (videoSurfaceHeight > videoSurfaceWidth) {
                    ratio = videoSurfaceHeight / parentHeight.toFloat()
                    videoSurfaceWidth /= ratio
                    videoSurfaceHeight /= ratio
                }
                videoSurfaceView.measure(MeasureSpec.makeMeasureSpec(videoSurfaceWidth.toInt(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(videoSurfaceHeight.toInt(), MeasureSpec.EXACTLY))

            }
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (::videoSurfaceView.isInitialized) {
            val videoSurfaceWidth = videoSurfaceView.measuredWidth
            val videoSurfaceHeight = videoSurfaceView.measuredHeight

            val videoSurfaceLeft = (right - left) / 2 - videoSurfaceWidth / 2
            val videoSurfaceTop = (bottom - top) / 2 - videoSurfaceHeight / 2
            val videoSurfaceRight = videoSurfaceLeft + videoSurfaceWidth
            val videoSurfaceBottom = videoSurfaceTop + videoSurfaceHeight

            videoSurfaceView.layout(videoSurfaceLeft, videoSurfaceTop, videoSurfaceRight, videoSurfaceBottom)
        }

    }

    fun setVideoSize (width: Int, height: Int) {
        videoSurfaceView.videoWidth = width
        videoSurfaceView.videoHeight = height
        videoSurfaceView.requestLayout()
    }
}