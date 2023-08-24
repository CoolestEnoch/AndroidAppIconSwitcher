package xposed.hkrpg.utils

import android.content.Context
import android.net.ConnectivityManager

// 获取屏幕分辨率
fun dp2px(context: Context, dpValue: Int): Int {
    val scale = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}


// 获取网络连接状态
fun isNetworkConnected(context: Context?): Boolean {
    if (context != null) {
        // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // 获取NetworkInfo对象
        val networkInfo = manager.activeNetworkInfo
        //判断NetworkInfo对象是否为空
        if (networkInfo != null) return networkInfo.isAvailable
    }
    return false
}