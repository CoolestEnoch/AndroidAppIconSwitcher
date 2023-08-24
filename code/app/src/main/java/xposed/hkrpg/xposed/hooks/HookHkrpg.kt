package xposed.hkrpg.xposed.hooks

import android.app.Activity
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.util.Base64
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam
import xposed.hkrpg.utils.SPLASH_12306
import xposed.hkrpg.utils.dp2px
import xposed.hkrpg.utils.runOnMainThread
import kotlin.concurrent.thread


fun hookHkrpg(lpparam: LoadPackageParam) {
    findMethod("com.mihoyo.combosdk.ComboSDKActivity") {
        name == "onCreate"
    }.hookAfter{
        val mContext = it.thisObject as Activity
        val mContentView = mContext.findViewById<ViewGroup>(android.R.id.content)

        // 生成自定义界面的view
        val ivCr200j = ImageView(mContext).apply {
            layoutParams = LinearLayout.LayoutParams(dp2px(mContext, 200), dp2px(mContext, 200))
            adjustViewBounds = true
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        val bytes: ByteArray = Base64.decode(SPLASH_12306, Base64.DEFAULT)
        val myBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        ivCr200j.background = BitmapDrawable(myBitmap)
        val mainLinearLayout = LinearLayout(mContext).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            ).apply {
            }
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.rgb(0, 0, 0))
            gravity = Gravity.CENTER

            addView(ivCr200j)
        }

        // 塞到contentView里
        mContentView.addView(mainLinearLayout)
        mainLinearLayout.bringToFront()
        thread {
            Thread.sleep(5000)
            runOnMainThread{
                mContentView.removeView(mainLinearLayout)
            }
        }

/*
        Toast.makeText(mContext, "hooked!", Toast.LENGTH_SHORT).show()
        val ivCr200j = ImageView(mContext).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            adjustViewBounds = true
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        Glide.with(mContext).load("https://greasyfork.org/vite/assets/blacklogo96-e0c2c761.png").into(ivCr200j)
        val videoView = VideoView(mContext).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setVideoPath(neverGonnaGiveYouUp)
            setMediaController(MediaController(mContext))
        }
        val dialogView = LinearLayout(mContext).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.VERTICAL
//            addView(ivCr200j)
            addView(videoView)
        }
        val alertDialog = AlertDialog.Builder(mContext).apply {
            setView(videoView)
//            setCancelable(false)
        }//.show()
//        videoView.start()
        */


        // 1.1中使用的方法：已废弃
        /*mContext.startActivity(Intent().apply {
            putExtras(Bundle().apply {
                putString("bg", "12306")
            })
            action = "xposed.hkrpg"
            component =
                ComponentName("xposed.hkrpg", "xposed.hkrpg.ui.CustomSplashActivity")
        })*/

        // 这样会从崩铁apk里找splash activity类，导致报ActivityNotFoundException
        /*mContext.startActivity(Intent(mContext, CustomSplashActivity::class.java).apply {
            putExtras(Bundle().apply {
                putString("bg", "12306")
            })
        })*/


        //Toast.makeText(mContext, "launching!", Toast.LENGTH_SHORT).show()
    }
}