package com.example.videoview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.videoview.video.VideoPlayerView

class MainActivity : AppCompatActivity() {

    private lateinit var videoPlayerView: VideoPlayerView
    private lateinit var buttonStartPlay: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        videoPlayerView = findViewById(R.id.video_player_view)
        buttonStartPlay = findViewById(R.id.button_start_play)
        buttonStartPlay.setOnClickListener {
            videoPlayerView.playVideo("https://app.coverr.co/s3/mp4/Footboys.mp4")
        }
    }
}
