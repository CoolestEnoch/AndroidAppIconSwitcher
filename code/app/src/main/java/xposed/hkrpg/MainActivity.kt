package xposed.hkrpg

import android.Manifest
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.permissionx.guolindev.PermissionX
import xposed.hkrpg.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isActive()) {
            binding.activeStatus.text = "已激活"
            Snackbar.make(window.decorView, "已激活", Snackbar.LENGTH_LONG).show()
        } else {
            binding.activeStatus.text = "未激活"
        }

        grantPermission(this)

        val flags = PackageManager.GET_ACTIVITIES or PackageManager.GET_SERVICES or
                PackageManager.GET_PROVIDERS or PackageManager.GET_RECEIVERS or
                PackageManager.MATCH_DIRECT_BOOT_AWARE or PackageManager.MATCH_DIRECT_BOOT_UNAWARE or
                PackageManager.GET_PERMISSIONS

        val pm = packageManager
        val appinfo: ApplicationInfo = pm.getApplicationInfo("com.MobileTicket", flags)
        binding.testtv.text = appinfo.loadLabel(pm) //= pm.getApplicationLabel(appinfo)
        binding.testtv.text = pm.getApplicationLabel(appinfo)
        binding.testiv.background = pm.getApplicationIcon(appinfo)
        binding.testiv.background = getApkIcon(this, "/sdcard/12306.apk")

        val a = pm.getPackageArchiveInfo("", flags)

    }

    fun isActive() = false


    fun getApkIcon(context: Context, apkPath: String?): Drawable? {
        val pm = context.packageManager
        val info = pm.getPackageArchiveInfo(
            apkPath!!,
            PackageManager.GET_ACTIVITIES
        )
        if (info != null) {
            val appInfo = info.applicationInfo
            appInfo.sourceDir = apkPath
            appInfo.publicSourceDir = apkPath
            try {
                return appInfo.loadIcon(pm)
            } catch (e: OutOfMemoryError) {
                Log.e("ApkIconLoader", e.toString())
            }
        }
        return null
    }

    private fun grantPermission(activity: AppCompatActivity) {
        //存储权限
        PermissionX.init(activity)
            .permissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
            )
            .explainReasonBeforeRequest()
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(deniedList, "保存图片所需权限", "好", "取消")
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    "不授予权限则可能出现闪退!",
                    "好",
                    "取消"
                )
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    Snackbar.make(
                        activity.window.decorView,
                        "权限状态正常",
                        Snackbar.LENGTH_LONG
                    )
                } else {
                    Snackbar.make(
                        activity.window.decorView,
                        "以下权限被拒绝: $deniedList",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }

    }
}