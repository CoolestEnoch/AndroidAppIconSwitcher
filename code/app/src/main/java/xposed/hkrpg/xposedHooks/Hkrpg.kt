package xposed.hkrpg.xposedHooks

import android.app.Activity
import android.app.AlertDialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import com.github.kyuubiran.ezxhelper.utils.isProtected
import com.github.kyuubiran.ezxhelper.utils.isPublic
import com.github.kyuubiran.ezxhelper.utils.paramCount
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam
import xposed.hkrpg.HkrpgSplashActivity
import java.lang.Exception
import kotlin.concurrent.thread

const val neverGonnaGiveYouUp =
    "https://vdse.bdstatic.com//192d9a98d782d9c74c96f09db9378d93.mp4?authorization=bce-auth-v1/40f207e648424f47b2e3dfbb1014b1a5/2021-07-12T02:14:24Z/-1/host/530146520a1c89fb727fbbdb8a0e0c98ec69955459aed4b1c8e00839187536c9"

fun hookHkrpg(lpparam: LoadPackageParam) {
    findMethod("com.mihoyo.combosdk.ComboSDKActivity") {
        name == "onCreate"
    }.hookAfter {
        val context = it.thisObject as Activity
        /*
        Toast.makeText(context, "hooked!", Toast.LENGTH_SHORT).show()
        val ivCr200j = ImageView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            adjustViewBounds = true
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        Glide.with(context).load("https://greasyfork.org/vite/assets/blacklogo96-e0c2c761.png").into(ivCr200j)
        val videoView = VideoView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setVideoPath(neverGonnaGiveYouUp)
            setMediaController(MediaController(context))
        }
        val dialogView = LinearLayout(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.VERTICAL
//            addView(ivCr200j)
            addView(videoView)
        }
        val alertDialog = AlertDialog.Builder(context).apply {
            setView(videoView)
//            setCancelable(false)
        }//.show()
//        videoView.start()
        */



        context.startActivity(Intent().apply {
            putExtras(Bundle().apply {
                putString("bg", "12306")
            })
            action = "xposed.hkrpg"
            component =
                ComponentName("xposed.hkrpg", "xposed.hkrpg.HkrpgSplashActivity")
        })



        //Toast.makeText(context, "launching!", Toast.LENGTH_SHORT).show()
    }
}