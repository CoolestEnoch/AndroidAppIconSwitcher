package xposed.hkrpg.ui

import android.app.Activity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.VideoView
import com.bumptech.glide.Glide
import xposed.hkrpg.R
import xposed.hkrpg.databinding.ActivityCustomSplashBinding
import xposed.hkrpg.utils.dp2px
import xposed.hkrpg.utils.neverGonnaGiveYouUp
import kotlin.concurrent.thread

class CustomSplashActivity : Activity() {

    private lateinit var binding: ActivityCustomSplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selector = intent.extras!!.getString("bg")

        if (selector == "12306") {
            Glide.with(this).load(R.mipmap.splash12306).into(binding.logo)
            binding.logo.layoutParams =
                LinearLayout.LayoutParams(dp2px(this, 200), dp2px(this, 200))
        }

        val videoView = VideoView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setVideoPath(neverGonnaGiveYouUp)
            setMediaController(MediaController(context))
        }

//        binding.root.addView(videoView)
//        videoView.start()

        thread {
            kotlin.runCatching {
                Thread.sleep(3000)
                finish()
            }
        }
    }
}