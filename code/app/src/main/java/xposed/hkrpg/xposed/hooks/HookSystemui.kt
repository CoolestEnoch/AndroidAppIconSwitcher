package xposed.hkrpg.xposed.hooks

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.util.Base64
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.window.SplashScreenView
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam
import xposed.hkrpg.utils.ICON_GENSHIN_IMPACT_BASE64
import xposed.hkrpg.utils.dp2px

fun hookSystemui(lpparam: LoadPackageParam) {
    // com.android.systemui中的方法
    // 需要Android 12及更高版本.在低版本上不进行hook
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
        findMethod("com.android.wm.shell.startingsurface.SplashscreenContentDrawer"){
            name == "makeSplashScreenContentView"
        }.hookAfter {
            val mSplashScreenView = it.result as SplashScreenView
            val mContext = it.args[0] as Context
//          Toast.makeText(mContext, "${mContext.packageName}", Toast.LENGTH_SHORT).show()

            val ivGenshinImpact = ImageView(mContext).apply {
                layoutParams = LinearLayout.LayoutParams(dp2px(mContext, 500), dp2px(mContext, 200))
                //layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                adjustViewBounds = true
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
            val bytes: ByteArray = Base64.decode(ICON_GENSHIN_IMPACT_BASE64, Base64.DEFAULT)
            val myBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            ivGenshinImpact.background = BitmapDrawable(myBitmap)

            mSplashScreenView.removeAllViews()
            mSplashScreenView.addView(
                LinearLayout(mContext).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                    orientation = LinearLayout.VERTICAL
                    setBackgroundColor(Color.rgb(255, 255, 255))
                    gravity = Gravity.CENTER

                    addView(ivGenshinImpact)
                }
            )
        }
    }


    // framework中的方法，在MIUI上不知道为什么一直不被调用
    /*findMethod("android.window.SplashScreenView\$Builder") {
        name == "build"
    }.hookAfter {
        val mSurfaceView = it.result as SurfaceView
        val mContext = mSurfaceView.context
        Toast.makeText(mContext, "Called SplashScreen.Build", Toast.LENGTH_SHORT).show()

        val splashView = it.result as SplashScreenView
        val mContext = splashView.context

        Toast.makeText(mContext, "Called SplashScreen.Build", Toast.LENGTH_SHORT).show()

        val ivGenshinImpact = ImageView(mContext).apply {
            layoutParams = LinearLayout.LayoutParams(dp2px(mContext, 500), dp2px(mContext, 200))
            //layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            adjustViewBounds = true
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        thread {
            val bitmap = Glide.with(mContext).asBitmap()
                .load("https://webstatic.mihoyo.com/bh3/upload/officialsites/201908/ys_1565764084_7084.png")
                .submit().get()
            runOnMainThread {
                ivGenshinImpact.background = BitmapDrawable(bitmap)
            }
        }

        splashView.removeAllViews()
        splashView.addView(
            LinearLayout(mContext).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
                orientation = LinearLayout.VERTICAL
                setBackgroundColor(Color.rgb(255, 255, 255))
                gravity = Gravity.CENTER

                addView(ivGenshinImpact)
            }
        )
        it.result = splashView
    }*/
}