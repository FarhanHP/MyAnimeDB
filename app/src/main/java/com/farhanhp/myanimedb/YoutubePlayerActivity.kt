package com.farhanhp.myanimedb

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.farhanhp.myanimedb.databinding.ActivityYoutubePlayerBinding
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class YoutubePlayerActivity : YouTubeBaseActivity() {
  private lateinit var binding: ActivityYoutubePlayerBinding
  private lateinit var youtuberPlayer: YouTubePlayerView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityYoutubePlayerBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val youtubeId = intent.getStringExtra(EXTRA_MESSAGE_VIDEO_ID)

    youtuberPlayer = binding.youtubePlayer
    youtuberPlayer.initialize(
      youtubeId,
      object: YouTubePlayer.OnInitializedListener{
        override fun onInitializationFailure(
          p0: YouTubePlayer.Provider?,
          p1: YouTubeInitializationResult?
        ) {
          Toast.makeText(applicationContext, "Fail to load video", Toast.LENGTH_SHORT).show()
          finish()
        }

        override fun onInitializationSuccess(
          p0: YouTubePlayer.Provider?,
          p1: YouTubePlayer?,
          p2: Boolean
        ) {
          p1?.loadVideo(youtubeId)
        }
      }
    )
  }

  companion object {
    const val EXTRA_MESSAGE_VIDEO_ID = "com.farhanhp.myanimedb.YoutubePlayerActivity.EXTRA_MESSAGE_VIDEO_ID"
  }
}