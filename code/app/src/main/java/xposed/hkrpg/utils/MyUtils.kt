package xposed.hkrpg.utils

import android.content.Context

fun dp2px(context: Context, dpValue: Int): Int {
    //获取屏幕分辨率
    val scale = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}