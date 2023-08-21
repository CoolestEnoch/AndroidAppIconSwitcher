package xposed.hkrpg.xposedHooks

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.github.kyuubiran.ezxhelper.utils.isPrivate
import com.github.kyuubiran.ezxhelper.utils.runOnMainThread
import de.robv.android.xposed.callbacks.XC_LoadPackage
import xposed.hkrpg.utils.dp2px
import kotlin.concurrent.thread


fun hook12306app(lpparam: XC_LoadPackage.LoadPackageParam){
    findMethod("com.MobileTicket.ui.dialog.SplashAdDialog"){
        name == "initView" && isPrivate
    }.hookAfter {
        val mDialog = it.thisObject as Dialog
        val contentView = mDialog.findViewById<ViewGroup>(android.R.id.content)
        val mContext = contentView.context

        contentView.removeAllViews()

//        Toast.makeText(mContext, "hooked!", Toast.LENGTH_SHORT).show()
        val ivCr200j = ImageView(mContext).apply {
            layoutParams = LinearLayout.LayoutParams(dp2px(mContext, 500), dp2px(mContext, 200))
            //layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            adjustViewBounds = true
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        thread {
            val bitmap = Glide.with(mContext).asBitmap().load("https://webstatic.mihoyo.com/upload/event/2022/07/29/c31dd1d732913e4ab5f3d4f03346a706_9097205533659112586.png").submit().get()
            runOnMainThread{
                ivCr200j.background = BitmapDrawable(bitmap)
            }
        }
        val dialogView = LinearLayout(mContext).apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.rgb(0,0,0))
            gravity = Gravity.CENTER
            addView(ivCr200j)
        }
        val webView = WebView(mContext).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    //使用WebView加载显示url
                    view.loadUrl(url)
                    //返回true
                    return true
                }
            }

            // loadUrl("https://sr.mihoyo.com/")
        }

        contentView.addView(dialogView)

        // views
/*        val image = it.thisObject.getObjectAs<ImageView>("image", ImageView::class.java)
        val skipButton = it.thisObject.getObjectAs<TextView>("skipButton", TextView::class.java)
        val adText = it.thisObject.getObjectAs<TextView>("adText", TextView::class.java)
        val imgLogo = it.thisObject.getObjectAs<ImageView>("imgLogo", ImageView::class.java)
        val imageContainer = it.thisObject.getObjectAs<FrameLayout>("imageContainer", FrameLayout::class.java)
        val mSurfaceView = it.thisObject.getObjectAs<SurfaceView>("mSurfaceView", SurfaceView::class.java)
        val scrollViewStub = it.thisObject.getObjectAs<ViewStub>("scrollViewStub", ViewStub::class.java)
        val shakeViewStub = it.thisObject.getObjectAs<ViewStub>("shakeViewStub", ViewStub::class.java)
        val buttonViewStub = it.thisObject.getObjectAs<ViewStub>("buttonViewStub", ViewStub::class.java)
        val clickScrollViewStub = it.thisObject.getObjectAs<ViewStub>("clickScrollViewStub", ViewStub::class.java)
        val imgTopLeftLogo = it.thisObject.getObjectAs<TextView>("imgTopLeftLogo", TextView::class.java)*/

//        Toast.makeText(mContext, "${skipButton.id}", Toast.LENGTH_SHORT).show()

//        contentView.removeAllViews()
    }
}